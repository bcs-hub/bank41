<script>
import BaseModal from '@/components/modal/BaseModal.vue'

export default {
  name: 'LocationInfoModal',
  components: { BaseModal },
  props: {
    locationInfoModalIsOpen: Boolean,
    location: Object,
  },
}
</script>

<template>
  <BaseModal :is-open="locationInfoModalIsOpen">
    <template #title> {{ location.locationName }} </template>

    <template #body>
      <div class="container text-center">
        <div class="row">
          <div class="col">
            <h6>Automaatide arv: {{ location.numberOfAtms }}</h6>
          </div>
          <div class="col">

          </div>
        </div>
      </div>
    </template>

    <template #button> </template>
  </BaseModal>
</template>
