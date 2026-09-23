<template>
  <div class="container text-center">
    <div class="row justify-content-start">
      <div class="col justify-content-start">
        <h6>{{ locationInfo.locationName }}</h6>
      </div>

    </div>

    <div class="row justify-content-center">
      <div class="col">
        <AtmImage image-data=""/>
        <div v-for="transactionType in locationInfo.transactionTypes">
          <p v-if="transactionType.isAvailable">{{ transactionType.transactionTypeName }}</p>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import AtmImage from '../../_components/image/AtmImage.vue'

export default {
  name: "LocationInfoCard",
  components: {AtmImage},
  props:{
    locationInfo: Object
  }
}
</script>
