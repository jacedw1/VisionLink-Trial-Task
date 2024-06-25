<script setup lang="ts">
import { apiFetch } from '~/api/api';


const points: any = useState('points')

const name = ref('')
const x = ref('')
const y = ref('')
const unique = computed( () => points.value.filter(pt => pt.name == name.value).length == 0 )
const valid = computed( () => name.value != '' && (x.value.toString().length != 0) && !isNaN(Number(x.value)) && (y.value.toString().length != 0) && !isNaN(Number(y.value)) )
const resetDisabled = computed( () => name.value == '' && x.value == '' && y.value == '' )
const saveDisabled = computed( () => resetDisabled.value || !unique.value || !valid.value)

async function save() {
    if(unique.value && valid.value) {
        await apiFetch('addPoint', { 
            method: 'POST',
            body: {
                name: name.value,
                x: Number(x.value),
                y: Number(y.value)
            }
        }).then(async() => {
                points.value = await apiFetch('/points', {
                    method: 'GET',
                    cache: 'no-store'
                })
                navigateTo('/')
            })
    }
}

function reset() {
    name.value = ''
    x.value = ''
    y.value = ''
}

function back() {
    navigateTo('/')
}

</script>


<template>
    <div class="relative overflow-x-auto">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <caption class="p-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
            Edit Point
        </caption>
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Name
                </th>
                <th scope="col" class="px-6 py-3">
                    X
                </th>
                <th scope="col" class="px-6 py-3">
                    Y
                </th>
            </tr>
        </thead>
        <tbody>
            <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                <td class="px-6 py-4">
                    <UInput v-model='name'/>
                </td>
                <td class="px-6 py-4">
                    <UInput v-model='x'/>
                </td>
                <td class="px-6 py-4">
                    <UInput v-model='y'/>
                </td>
            </tr>
        </tbody>
            <div>
                <UButton color='green' @click='back'>
                    Back
                </UButton>
                <UButton color='green' @click='reset' :disabled='resetDisabled'>
                    Reset
                </UButton>
                <UButton color='green' @click='save' :disabled='saveDisabled'>
                    Save
                </UButton>
            </div>
    </table>
</div>
</template>