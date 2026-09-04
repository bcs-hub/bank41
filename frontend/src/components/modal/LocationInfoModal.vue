<script>
import BaseModal from '@/components/modal/BaseModal.vue'
import AtmImage from '@/components/image/AtmImage.vue'
import TransactionTypesCheckbox from '@/components/location/TransactionTypesCheckbox.vue'

export default {
  name: 'LocationInfoModal',
  components: { TransactionTypesCheckbox, AtmImage, BaseModal },
  props: {
    locationInfoModalIsOpen: Boolean,
    location: Object,
  },
  emits: ['event-location-info-modal-closed'],
}
</script>

<template>
  <BaseModal
    :is-open="locationInfoModalIsOpen"
    @event-modal-closed="$emit('event-location-info-modal-closed')"
  >
    <template #title>{{ location.locationName }}</template>

    <template #body>
      <h6>Automaatide arv: {{ location.numberOfAtms }}</h6>
      <div class="container text-center">
        <div class="row">
          <div class="col">
            <TransactionTypesCheckbox
              :transaction-types="location.transactionTypes"
              :switches-disabled="true"
            />
          </div>
          <div class="col"><AtmImage :image-data="location.imageData" /></div>
        </div>
      </div>
    </template>

    <template #buttons> </template>
  </BaseModal>
</template>
