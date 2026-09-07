<script>
export default {
  name: 'LocationsTable',
  props: {
    locations: {},
  },
  emits: ['event-location-name-click'],
}
</script>

<template>
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
        <td>
          <div
            @click="$emit('event-location-name-click', location.locationId)"
            class="green pointer text-pointer-underlined"
          >
            {{ location.locationName }}
          </div>
        </td>
        <td>
          <div
            v-for="transactionType in location.transactionTypes"
            :key="transactionType.transactionTypeId"
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
