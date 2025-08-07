<template>
  <div class="card">    
    <Stepper :value="activeStep">
        <StepItem value="0">
        <Step>
          <div @click="toggleStep('1')" class="cursor-pointer font-semibold">Fold All</div>
        </Step>
        <StepPanel v-show="activeStep === '1'">       
        </StepPanel>
      </StepItem>

      <!-- Step 1: Owner -->
      <StepItem value="1">
        <Step>
          <div @click="toggleStep('1')" class="cursor-pointer font-semibold">Owner</div>
        </Step>
        <StepPanel v-show="activeStep === '1'">
          <Owner />
          <div class="py-6">
            <Button label="Next" @click="activeStep = '2'" />
          </div>
        </StepPanel>
      </StepItem>

      <!-- Step 2: Vehicle -->
      <StepItem value="2">
        <Step>
          <div @click="toggleStep('2')" class="cursor-pointer font-semibold">Vehicle</div>
        </Step>
        <StepPanel v-show="activeStep === '2'">
          <Vehicle />
          <div class="flex py-6 gap-2">
            <Button label="Back" severity="secondary" @click="activeStep = '1'" />
            <Button label="Next" @click="activeStep = '3'" />
          </div>
        </StepPanel>
      </StepItem>

      <!-- Step 3: Role -->
      <StepItem value="3">
        <Step>
          <div @click="toggleStep('3')" class="cursor-pointer font-semibold">Role</div>
        </Step>
        <StepPanel v-show="activeStep === '3'">
          <Role />
          <div class="py-6">
            <Button label="Back" severity="secondary" @click="activeStep = '2'" />
            <Button label="Next" @click="activeStep = '4'" />
          </div>
        </StepPanel>
      </StepItem>

      <!-- Step 4: Permission -->
      <StepItem value="4">
        <Step>
          <div @click="toggleStep('4')" class="cursor-pointer font-semibold">Permission</div>
        </Step>
        <StepPanel v-show="activeStep === '4'">
          <Permission />
          <div class="py-6">
            <Button label="Back" severity="secondary" @click="activeStep = '3'" />
          </div>
        </StepPanel>
      </StepItem>
    </Stepper>
  </div>
</template>

<script setup>
import Owner from './Owner.vue'
import Vehicle from './Vehicle.vue'
import Permission from './Permission.vue'
import Role from './Role.vue'

import { ref } from 'vue'

const activeStep = ref('') // nothing shown initially

const toggleStep = (step) => {
  activeStep.value = activeStep.value === step ? '' : step
}
</script>

<style scoped>
/* You can customize hover/focus effects here */
.cursor-pointer:hover {
  color: #4f46e5; /* Indigo-600 or any accent color */
}
</style>
