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
    <template #title>{{ location.locationName }} </template>

    <template #body>
      <h5>Automaatide arv: {{ location.numberOfAtms }}</h5>
      <div class="container text-center">
        <div class="row">
          <div class="col">Column</div>
        </div>
      </div>
    </template>


    <template #buttons> </template>
  </BaseModal>
</template>
