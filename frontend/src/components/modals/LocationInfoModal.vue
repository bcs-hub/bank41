<script>
import BaseModal from '@/components/modals/BaseModal.vue'
import AtmImage from '@/components/location/AtmImage.vue'
import TransactionTypesCheckbox from '@/components/forms/TransactionTypesCheckbox.vue'

export default {
  name: 'LocationInfoModal',
  components: { TransactionTypesCheckbox, AtmImage, BaseModal },
  props: {
    locationInfoModalIsOpen: Boolean,
    location: Object,
  },
  emits:['event-location-info-modal-closed']
}
</script>

<template>
  <BaseModal :is-open="locationInfoModalIsOpen" @event-modal-closed="$emit('event-location-info-modal-closed')">
    <template #title>{{ location.locationName }} </template>

    <template #body>
      <div class="row">
        <h5>Automaatide Arv {{ location.numberOfAtms }}</h5>

        <div class="col-5">
          <TransactionTypesCheckbox
            :transaction-types="location.transactionTypes"
            :switches-disabled="true"
          />
        </div>
        <div class="col-5"><AtmImage :image-data="location.imageData" /></div>
      </div>
    </template>

    <template #buttons> </template>
  </BaseModal>
</template>
