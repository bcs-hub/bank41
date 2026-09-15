export const PRIVATE_PREFIXES = ['/private', '//']
export const SECRET_PATTERN = /(-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----|\b(?:sk|rk|pk)_(?:live|test)_[A-Za-z0-9]{16,}|\bgh[oprsu]_[A-Za-z0-9]{20,}|\bAKIA[0-9A-Z]{16}\b|(?:api[_-]?key|password|passwd|secret|token)\s*[:=]\s*[^\s]{8,})/i

export function skipReason(prompt) {
  if (typeof prompt !== 'string' || !prompt.trim()) return 'empty prompt'
  if (PRIVATE_PREFIXES.some((prefix) => prompt.startsWith(prefix))) return 'private prefix'
  if (SECRET_PATTERN.test(prompt)) return 'possible secret detected'
  return null
}
