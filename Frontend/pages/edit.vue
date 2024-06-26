<script setup lang="ts">
import { apiFetch } from '~/api/api';


const points: any = useState('points')
const current: any = useState('current').value
const oldName: string = current.name
const oldX: string = current.x.toString()
const oldY: string = current.y.toString()

const name = ref(current.name)
const x = ref(current.x)
const y = ref(current.y)
const unique = computed( () => points.value.filter(pt => pt.name != oldName && pt.name == name.value).length == 0 )
const valid = computed( () => !isNaN(Number(x.value)) && !isNaN(Number(y.value)) )
const resetDisabled = computed( () => name.value == oldName && x.value == oldX && y.value == oldY )
const saveDisabled = computed( () => resetDisabled.value || !unique.value || !valid.value)
const rows1 = computed( () => findClosestPoint({name: name.value, x: x.value, y: y.value}) )
const rows2 = computed( () => findFurthestPoint({name: name.value, x: x.value, y: y.value}) )
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
}, {
    key: 'distance',
    label: 'Distance'
}]

async function save() {
    if(unique.value && valid.value) {
        await apiFetch('editPoint/' + oldName, { 
            method: 'PUT',
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
    name.value = oldName
    x.value = oldX
    y.value = oldY
}

function back() {
    navigateTo('/')
}

async function del() {
    await apiFetch('deletePoint/' + oldName, { method: 'DELETE' })
        .then(async() => {
            points.value = await apiFetch('/points', {
                method: 'GET',
                cache: 'no-store'
            })
            navigateTo('/')
        })
    
}

function findClosestPoint(cur: any) {
    const newList = []

    var pts = points.value
    for(var point in pts) {
        var pt = pts[point]
        if(pt.name != name.value && pt.name != cur.name) {
            var dist = distanceBetweenTwoPoints(pt, cur)
            pt.distance = Math.round(dist * 10) / 10
            newList.push(pt)
        }
    }
    var currentClosest = []
    for(var point in newList) {
        var pt = newList[point]
        if(currentClosest.length == 0) {
            currentClosest.push(pt)
        } else {
            if(currentClosest[0].distance == pt.distance) {
                currentClosest.push(pt)
            } else if(currentClosest[0].distance > pt.distance) {
                currentClosest = [pt]
            }
        }
    }
    return currentClosest
}
function findFurthestPoint(cur: any) {
    const newList = []

    var pts = points.value
    for(var point in pts) {
        var pt = pts[point]
        if(pt.name != name.value && pt.name != cur.name) {
            var dist = distanceBetweenTwoPoints(pt, cur)
            pt.distance = Math.round(dist * 10) / 10
            newList.push(pt)
        }
    }
    var currentFurthest = []
    for(var point in newList) {
        var pt = newList[point]
        if(currentFurthest.length == 0) {
            currentFurthest.push(pt)
        } else {
            if(currentFurthest[0].distance == pt.distance) {
                currentFurthest.push(pt)
            } else if(currentFurthest[0].distance < pt.distance) {
                currentFurthest = [pt]
            }
        }
    }
    return currentFurthest
}

function distanceBetweenTwoPoints(point1: any, point2: any) {
    return Math.sqrt(Math.pow((Number(point1.x) - Number(point2.x)), 2) + Math.pow((Number(point1.y) - Number(point2.y)), 2))
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
                <UButton color='red' @click='del'>
                    Delete
                </UButton>
            </div>
    </table>
    <div>
        <div class="p-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
            Current Closest
        </div>
        <UTable 
            :columns="columns" 
            :rows="rows1">
        </UTable>
    </div>
    <div>
        <div class="p-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
            Current Furthest
        </div>
        <UTable 
            :columns="columns" 
            :rows="rows2">
        </UTable>
    </div>
</div>
</template>