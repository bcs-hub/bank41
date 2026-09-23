<template>
  <div class="location-cards">
    <div v-if="locations.length === 0" class="text-muted text-center py-4">
      Asukohti ei leitud
    </div>
    <div
      v-for="location in locations"
      :key="location.locationId"
      class="location-card-wrapper"
      :class="{ 'is-active': activeLocationId === location.locationId }"
      @click="$emit('event-location-selected', location)"
    >
      <LocationCard :location="location" />
    </div>
  </div>
</template>

<script>
import LocationCard from './LocationCard.vue'

export default {
  name: 'LocationCards',
  components: { LocationCard },
  props: {
    locations: {
      type: Array,
      default: () => [],
    },
    activeLocationId: {
      type: Number,
      default: null,
    },
  },
  emits: ['event-location-selected'],
}
</script>

<style scoped>
.location-cards {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.location-card-wrapper {
  padding: 12px;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  background: #fff;
  cursor: pointer;
  transition: all 0.15s ease;
}

.location-card-wrapper:hover {
  border-color: #86b7fe;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.location-card-wrapper.is-active {
  border-color: #0d6efd;
  box-shadow: 0 0 0 2px rgba(13, 110, 253, 0.25);
  background-color: #f0f6ff;
}
</style>
