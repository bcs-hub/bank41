<script>
export default {
  name: 'AtmImage',
  props: {
    location: Object,
  },
}
</script>

<template>
  <div>
    <img
      v-if="location.imageData === ''"
      src="../../assets/images/atm.png"
      class="img-thumbnail"
      alt="panga automaadi pilt"
    />
    <img v-else :src="location.imageData" class="img-thumbnail" alt="panga automaadi pilt" />
  </div>
</template>
