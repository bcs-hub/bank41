<script>
export default {
  name: 'AtmImage',
  props: {
    location: {}
  }
}
</script>

<template>
  <div>
  <img v-if="location.imageData === ''"
       src="../../assets/images/atm.png"
       class="img-thumbnail"
       alt="Pangaautomaadi pilt"/>
  <img v-else :src="location.imageData" class="img-thumbnail" alt="Pangaautomaadi pilt">
  </div>
</template>
