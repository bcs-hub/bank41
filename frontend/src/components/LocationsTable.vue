<script>
export default {
  name: 'LocationsTable',
  props: {
    locations: {}
  }
}
</script>

<template>
  <div class="col">
    <!-- todo  SIIN ON ASUKOHTADE TABEL     -->
    <table class="table table-dark table-hover">
      <thead>
      <tr>
        <th scope="col">Linn</th>
        <th scope="col">Asukoht</th>
        <th scope="col">Teenused</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="location in locations" :key="location.locationId">
        <td>{{ location.cityName }}</td>
        <td>{{ location.locationName }}</td>
        <td>
          <template
              v-for="transactionType in location.transactionTypes"
              :key="transactionType.transactionTypeId"
          >
            <div v-if="transactionType.isAvailable">
              {{ transactionType.transactionTypeName }}
            </div>
          </template>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>