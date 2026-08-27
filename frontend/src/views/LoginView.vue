<script>
export default {
  name: 'LoginView',
}
</script>

<template>
  <div class="container text-center">
    <div class="row justify-content-center">
      <div class="col col-3">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" placeholder="" />
          <label>Kasutajanimi</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" placeholder="" />
          <label>Parool</label>
        </div>
        <button type="submit" class="btn btn-primary">Logi sisse</button>
      </div>
    </div>
  </div>
</template>
