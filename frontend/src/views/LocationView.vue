<script>
import CitiesDropdown from "@/components/CitiesDropdown.vue";

export default {
  name: 'LocationView',
  components: {CitiesDropdown},
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col">
        <h1>Lisa asukoht</h1>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown/>
      </div>
      <div class="col">
        väljad placeholder
      </div>
      <div class="col">
        pilt
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col">
        <button class="btn btn-secondary" type="submit">Tagasi</button>
        <button class="btn btn-success" type="submit">Lisa</button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
