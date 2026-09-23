<template>
  <div>
    <img v-if="imageData === null || imageData === ''" src="../../_assets/images/atm.png" class="img-thumbnail" />
    <img v-else :src="imageData" class="img-thumbnail" />
  </div>
</template>

<script>
export default {
  name: 'AtmImage',
  props: {
    imageData: String,
  },
}
</script>
