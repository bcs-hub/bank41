<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'

export default {
  name: 'LocationForm',
  components: { CitiesDropdown },
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown :cities/>
      </div>
      <div class="col">väljad</div>
      <div class="col">img placeholder</div>
    </div>
  </div>
</template>

<style scoped></style>
