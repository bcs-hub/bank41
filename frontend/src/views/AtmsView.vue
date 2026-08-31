<script>
export default {
  name: 'AtmsView',
  data() {
    return {
      userId: sessionStorage.getItem('userId'),
      roleName: sessionStorage.getItem('roleName'),
    }
  },
  methods: {
    getCities() {
      alert("toon andmeid")
    },
  },
  beforeMount() {
    this.getCities()
  },

}
</script>

<template>
  <div class="container text-center">
    <div class="row mt-2">
      <div class="col mb-5">
        <h1>Pangaautomaadid</h1>
      </div>
    </div>
    <div class="row row">
      <div class="col col-2">
        <select class="form-select" aria-label="Default select example">
          <option value="0">Kõik linnad</option>
          <option value="2">Tallinn</option>
          <option value="1">Tartu</option>
          <option value="3">Pärnu</option>
        </select>
      </div>
      <div class="col">Tabel</div>
    </div>
  </div>
</template>
