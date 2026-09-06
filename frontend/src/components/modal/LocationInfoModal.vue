<script>
import BaseModal from '@/components/modal/BaseModal.vue'
import TransactionTypesCheckbox from '@/components/location/TransactionTypesCheckbox.vue'
import AtmImage from '@/components/image/AtmImage.vue'

export default {
  name: 'LocationInfoModal',
  components: { AtmImage, TransactionTypesCheckbox, BaseModal },
  props: {
    locationInfoModalIsOpen: Boolean,
    location: Object,
  },
}
</script>

<template>
  <BaseModal
    :is-open="locationInfoModalIsOpen"
    @event-modal-closed="$emit('event-location-info-modal-closed')"
  >
    <template #title>{{ location.locationName }}</template>

    <template #body>
      <div class="container text-start">
        <div class="row">
          <div class="col justify-content-start  ms-4 mt-2 mb-2">
            <h5>Automaatide arv: {{ location.numberOfAtms }}</h5>
          </div>
        </div>
      </div>
      <div class="container text-center">
        <div class="row justify-content-center">
          <div class="col">
            <div>
              <TransactionTypesCheckbox
                :transaction-types="location.transactionTypes"
                :switches-disabled="true"
              />
            </div>
          </div>
          <div class="col">
            <div>
                <AtmImage :image-data="location.imageData" />
            </div>
          </div>
        </div>
      </div>
    </template>
  </BaseModal>
</template>
