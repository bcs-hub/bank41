#!/usr/bin/env node
import { appendFile } from 'node:fs/promises'
import { homedir } from 'node:os'
import { join } from 'node:path'
import { pathToFileURL } from 'node:url'
import { skipReason } from './prompt-safety.mjs'

const logPath = process.env.LIVE_PROMPT_LOG_PATH || join(homedir(), '.live-prompt-board.log')
async function log(message) { try { await appendFile(logPath, `${new Date().toISOString()} ${message}\n`) } catch { /* A logging failure must not block the editor. */ } }

export async function publishPrompt(content, fetchImpl = fetch) {
  const reason = skipReason(content)
  if (reason) return { skipped: true, reason }
  const url = process.env.LIVE_PROMPT_PUBLISH_URL
  const token = process.env.LIVE_PROMPT_PUBLISH_TOKEN
  if (!url || !token) throw new Error('LIVE_PROMPT_PUBLISH_URL and LIVE_PROMPT_PUBLISH_TOKEN are required')
  const response = await fetchImpl(url, {
    method: 'POST',
    headers: { authorization: `Bearer ${token}`, 'content-type': 'application/json' },
    body: JSON.stringify({ content }),
    signal: AbortSignal.timeout(4500),
  })
  if (!response.ok) throw new Error(`publish endpoint returned HTTP ${response.status}: ${(await response.text()).slice(0, 300)}`)
  return response.json()
}

async function main() {
  let input = ''
  for await (const chunk of process.stdin) input += chunk
  try {
    const event = JSON.parse(input)
    const content = event.prompt ?? event.user_prompt
    const result = await publishPrompt(content)
    if (result.skipped) await log(`Skipped: ${result.reason}`)
  } catch (error) {
    await log(`Publish failed: ${error instanceof Error ? error.message : String(error)}`)
  }
  process.exitCode = 0
}

if (import.meta.url === pathToFileURL(process.argv[1] ?? '').href) await main()
