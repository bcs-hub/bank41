<script>
import { PhPencil } from '@phosphor-icons/vue'
import NavigationService from "@/services/NavigationService.js";

export default {
  name: 'LocationsTable',
  components: { PhPencil },
  props: {
    locations: {},
    roleName: String
  },
  methods: {
    navigateToLocationViewAsEdit(locationId) {
      NavigationService.navigateToLocationViewAsEdit(locationId)
    },
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
        <th scope="col"></th>
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
        <td>
          {{ location.locationId }}
          <PhPencil v-if="roleName === 'admin' "
            @click="navigateToLocationViewAsEdit(location.locationId)"
            :size="32"
            class="green pointer"
          />
        </td>
      </tr>
    </tbody>
  </table>
</template>
