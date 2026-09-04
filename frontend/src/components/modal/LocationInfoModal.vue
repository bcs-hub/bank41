<script>
import BaseModal from '@/components/modal/BaseModal.vue'

export default {
  name: 'LocationInfoModal',
  components: { BaseModal },
  props: {
    locationInfoModalIsOpen: {},
  },
}
</script>

<template>
  <BaseModal :is-open="locationInfoModalIsOpen">
    <template #title> </template>

    <template #body> </template>

    <template #buttons> </template>
  </BaseModal>
</template>
