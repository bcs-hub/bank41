<script>
import { PhProhibit } from '@phosphor-icons/vue'

export default {
  name: 'NotAuthorizedView',
  components: { PhProhibit },
}
</script>

<template>
  <div class="container text-center">
    <div class="row">
      <div class="col">
        <h1>Ligipääs keelatud! <PhProhibit :size="52" /></h1>
        <img
          src="https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExYndlam1iNTYyd3ljOHB2YjAweG04bXhnbm1nYnh3OGlsbXJzbmpxNiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/f8Gk0YteGgcqaEktvD/giphy.gif"
          alt="Ligipääs keelatud"
        />
      </div>
    </div>
  </div>
</template>
