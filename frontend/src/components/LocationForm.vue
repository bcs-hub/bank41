<script>
import CitiesDropdown from '@/components/CitiesDropdown.vue'

export default {
  name: 'LocationForm',
  components: { CitiesDropdown },
  props: {
    cities: Array
  }
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-2">
        <CitiesDropdown :cities="cities" />
      </div>
      <div class="col">väljad placeholder</div>
      <div class="col">pilt</div>
    </div>
  </div>
</template>
