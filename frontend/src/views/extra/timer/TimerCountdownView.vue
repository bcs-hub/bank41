<template>
  <div>
    <h1>Timer countdown</h1>
    <!-- Input for setting the timer duration in minutes -->
    <input v-model.number="timerInMinutes" type="number" min="1" placeholder="Minutes" />

    <!-- Display the formatted countdown timer -->
    <h1>{{ formattedTime }}</h1>

    <!-- Button to start the timer, disabled when timer is running -->
    <button @click="startTimer" :disabled="isRunning" class="btn btn-outline-success">Start Timer</button>
  </div>
</template>

<script>

export default {
  name: "TimerCountdownView",
  data() {
    return {
      modalIsOpen: false, // Controls the visibility of the modal
      startTimeMilliseconds: 0, // Stores the timestamp when the timer starts
      timerInMinutes: 3, // User-defined duration of the timer in minutes
      remainingTime: 0, // Remaining time in milliseconds
      isRunning: false, // Indicates whether the timer is currently running
      interval: null // Stores the interval ID for clearing the timer
    };
  },
  computed: {
    formattedTime() {
      // Converts remaining milliseconds into minutes, seconds, and milliseconds for display
      const minutes = Math.floor(this.remainingTime / 60000);
      const seconds = Math.floor((this.remainingTime % 60000) / 1000);
      const milliseconds = Math.floor((this.remainingTime % 1000) / 10);
      return `${minutes}:${seconds.toString().padStart(2, '0')}.${milliseconds.toString().padStart(2, '0')}`;
    }
  },
  methods: {
    startTimer() {
      if (this.isRunning) return; // Prevent multiple timers from starting

      this.isRunning = true; // Set running flag to true
      this.modalIsOpen = false; // Ensure the modal is closed when restarting the timer
      this.startTimeMilliseconds = Date.now(); // Capture the start time
      this.remainingTime = this.timerInMinutes * 60000; // Convert user input into milliseconds

      // Start interval to update the timer every 50ms for smooth countdown
      this.interval = setInterval(() => {
        const elapsedTime = Date.now() - this.startTimeMilliseconds; // Calculate elapsed time
        this.remainingTime = Math.max(this.timerInMinutes * 60000 - elapsedTime, 0); // Update remaining time

        if (this.remainingTime === 0) {
          clearInterval(this.interval); // Stop the timer when it reaches zero
          this.isRunning = false; // Reset running flag
          alert("Aeg on läbi!")
        }
      }, 50);
    }
  },
  beforeUnmount() {
    if (this.interval) {
      clearInterval(this.interval); // Clear the timer interval when component is unmounted
    }
  }
};
</script>
