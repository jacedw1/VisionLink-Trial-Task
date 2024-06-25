<script setup lang="ts">
import { useState } from "#app";
import { apiFetch } from '~/api/api';

const points = useState('points')
const currentPoint = useState('current')
await callOnce(async () => {
  points.value = await apiFetch('/points', {
    method: 'GET',
    cache: 'no-store'
  })
})
const columns = [{
    key: 'name',
    label: 'Name',
    sortable: true
}, {
    key: 'x',
    label: 'X',
    sortable: true
}, {
    key: 'y',
    label: 'Y',
    sortable: true
}]

function selectRow(row: any) {
    console.log(points)
    currentPoint.value = row
    if(row) {
        navigateTo('/edit')
    }
}

function add() {
    navigateTo('/add')
}
</script>

<template>
    <div class="h-screen">
        <UTable 
            :columns="columns" 
            :rows="points"
            @select="selectRow">
        </UTable>
        <UButton color='green' @click='add'>
            Add Point
        </UButton>
    </div>
</template>