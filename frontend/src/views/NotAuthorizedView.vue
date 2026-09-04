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
        <h1>Ligipääs keelatud! <PhProhibit class="bg-danger" :size="50" /></h1>
        <img class="img-fluid"
          src="https://media.tenor.com/7J77iJcHMA8AAAAM/penguins-madagascar.gif"
          alt="Pingviinid kaklevad. Ligipääs keelatud "
        />
      </div>
    </div>
  </div>
</template>
