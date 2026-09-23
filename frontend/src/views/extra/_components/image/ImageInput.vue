<template>
  <div class="mb-3">
    <div class="input-group">
      <input
        ref="fileInput"
        class="form-control"
        type="file"
        @change="handleImage"
        accept="image/x-png,image/jpeg,image/gif"
      />
      <button class="btn btn-outline-danger" type="button" @click="clearFileInput">
        <font-awesome-icon icon="fa-solid fa-trash" />
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ImageInput',
  props: {
    resetFileInput: Boolean,
  },
  watch: {
    resetFileInput(newValue) {
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
        this.$emit('event-new-image-selected', reader.result)
      }
      reader.onerror = (error) => {
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
}
</script>
