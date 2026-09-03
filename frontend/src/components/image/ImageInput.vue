<script>
import { PhTrash } from '@phosphor-icons/vue'

export default {
  name: 'ImageInput',
  components: { PhTrash },
  props: {
    resetImageInput: Boolean,
  },
  watch: {
    resetImageInput(newValue) {
      if (newValue) {
        this.clearFileInput()
      }
    },
  },
  methods: {
    handleImage(event) {
      const selectedImage = event.target.files[0]
      this.emitBase64(selectedImage)
    },

    emitBase64(fileObject) {
      const reader = new FileReader()
      reader.onload = () => {
        let imageDataBase64 = reader.result
        this.$emit('event-new-image-selected', imageDataBase64)
      }
      reader.onerror = function (error) {
        alert(error)
      }
      reader.readAsDataURL(fileObject)
    },

    clearFileInput() {
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = ''
        this.$emit('event-chosen-image-cleared')
      }
    },
  },
  emits: ['event-new-image-selected', 'event-chosen-image-cleared'],
}
</script>

<template>
  <div class="mb-3">
    <div class="input-group">
      <input
        ref="fileInput"
        class="form-control"
        type="file"
        @change="handleImage($event)"
        accept="image/x-png,image/jpeg,image/gif"
      />
      <button class="btn btn-outline-danger" type="button" @click="clearFileInput">
        <PhTrash :size="32" />
      </button>
    </div>
  </div>
</template>
