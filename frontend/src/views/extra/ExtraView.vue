<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-auto">

        <div class="mb-4">
          <h6 class="text-muted text-uppercase mb-2">Basic kaardid</h6>
          <button @click="$router.push({name: 'mapAddPinRoute'})" type="button" class="btn btn-outline-primary me-2">Pin'i lisamine</button>
          <button @click="$router.push({name: 'mapSimpleRoute'})" type="button" class="btn btn-outline-primary me-2">Vaata kaarti</button>
        </div>

        <div class="mb-4">
          <h6 class="text-muted text-uppercase mb-2">Maakonnaga kaardid</h6>
          <button @click="$router.push({name: 'mapApiCountyRoute'})" type="button" class="btn btn-outline-primary me-2">Kaart API</button>
          <button @click="$router.push({name: 'mapJsonCountyRoute'})" type="button" class="btn btn-outline-primary me-2">Kaart JSON</button>
          <button @click="$router.push({name: 'mapJsonCountyPlainRoute'})" type="button" class="btn btn-outline-primary me-2">Kaart JSON (PLAIN)</button>
        </div>

        <div class="mb-4">
          <h6 class="text-muted text-uppercase mb-2">ATM asukoha kaardid</h6>
          <button @click="$router.push({name: 'mapLocationRoute'})" type="button" class="btn btn-outline-primary me-2">ATM asukoha lisamine</button>
          <button @click="$router.push({name: 'mapAtmsRoute'})" type="button" class="btn btn-outline-primary me-2">ATM asukohad</button>
        </div>

        <div class="mb-4">
          <h6 class="text-muted text-uppercase mb-2">Muud</h6>
          <button @click="$router.push({name: 'mapTimerRoute'})" type="button" class="btn btn-outline-secondary me-2">Timer countdown</button>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ExtraView',
  data() {
    return {}
  },
  methods: {},
  mounted() {
  }
}
</script>
