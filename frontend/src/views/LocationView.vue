<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'

export default {
  name: 'LocationView',
  components: { CitiesDropdown },
}
</script>

<template>
  <div class="container text-center row-gap-3 d-grid border p-4">
    <div class="row">
      <div class="col">
        <h1>Lisa asukoht</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-3">
        <CitiesDropdown />
      </div>
      <div class="col">väljad</div>
      <div class="col">pilt</div>
    </div>
    <div class="row justify-content-center gx-4">
      <div class="col col-12 d-flex gap-2 justify-content-center">
        <button type="button" class="btn btn-secondary">Tagasi</button>
        <button type="submit" class="btn btn-success">Lisa</button>
      </div>
    </div>
  </div>
</template>
