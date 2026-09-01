<script>
export default {
  name: 'LocationsTable',
  props: {
    locations: {}
  }
}
</script>

<template>
  <table class="table table-hover table-bordered table-light">
    <thead>
    <tr>
      <th scope="col">Linn</th>
      <th scope="col">Asukoht</th>
      <th scope="col">Teenused</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="location in locations" :key="location.locationId">
      <th scope="row">{{ location.cityName }}</th>
      <td>{{ location.locationName }}</td>
      <td>
        <div
            v-for="transactionType in location.transactionTypes"
            :key="transactionType.transactionTypeId"
            class="text-left"
        >
          <div v-if="transactionType.isAvailable">
            {{ transactionType.transactionTypeName }}
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</template>