<script setup>
import { ref } from 'vue';

const number1 = ref(null);
const number2 = ref(null);
const result = ref(null);

function addNumbers() {
    result.value = (Number(number1.value) || 0) + (Number(number2.value) || 0);
}
</script>

<template>
    <div class="card flex flex-col gap-4">
        <div class="font-semibold text-xl mb-4">Addition</div>
        <div class="flex flex-col gap-2">
            <label for="number1">First Number</label>
            <InputText id="number1" type="number" v-model.number="number1" />
        </div>
        <div class="flex flex-col gap-2">
            <label for="number2">Second Number</label>
            <InputText id="number2" type="number" v-model.number="number2" />
        </div>
        <Button label="Add" @click="addNumbers" />
        <div v-if="result !== null" class="text-lg mt-2">Result: {{ result }}</div>
    </div>
</template>

<style scoped></style>
