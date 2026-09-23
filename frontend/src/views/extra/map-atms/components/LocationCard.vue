<template>
  <div class="location-card">
    <h6 class="location-name">{{ location.locationName }}</h6>
    <div class="image-wrapper mb-2">
      <img
        v-if="location.imageData"
        :src="location.imageData"
        class="img-thumbnail location-img"
        alt="ATM pilt"
      />
      <img
        v-else
        src="../../_assets/images/atm.png"
        class="img-thumbnail location-img"
        alt="ATM pilt"
      />
    </div>
    <div class="atm-count mb-2">
      <span class="badge bg-secondary">{{ location.numberOfAtms }} ATM</span>
    </div>
    <ul class="list-unstyled transaction-types mb-0">
      <li
        v-for="tt in location.transactionTypes"
        :key="tt.transactionTypeId"
        class="transaction-type-item"
        :class="tt.isAvailable ? 'available' : 'unavailable'"
      >
        <span class="status-dot"></span>
        {{ tt.transactionTypeName }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'LocationCard',
  props: {
    location: {
      type: Object,
      required: true,
    },
  },
}
</script>

<style scoped>
.location-card {
  min-width: 180px;
  max-width: 240px;
  text-align: left;
}

.location-name {
  font-weight: 600;
  margin-bottom: 8px;
  color: #2c3e50;
  border-bottom: 1px solid #dee2e6;
  padding-bottom: 4px;
}

.image-wrapper {
  text-align: center;
}

.location-img {
  width: 100%;
  max-height: 120px;
  object-fit: cover;
}

.transaction-type-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 0.82rem;
  padding: 2px 0;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  flex-shrink: 0;
}

.available .status-dot {
  background-color: #28a745;
}

.unavailable {
  color: #adb5bd;
}

.unavailable .status-dot {
  background-color: #dee2e6;
}
</style>
