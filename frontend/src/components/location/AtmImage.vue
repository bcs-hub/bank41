<script>
export default {
  name: 'AtmImage',
  props: {
    imageData: String,
  },
}
</script>

<template>
  <div>
    <img
      v-if="imageData === ''"
      src="../../assets/images/atm.png"
      class="img-thumbnail"
      alt="panga automaadi pilt"
    />
    <img v-else :src="imageData" class="img-thumbnail" alt="panga automaadi pilt" />
  </div>
</template>
