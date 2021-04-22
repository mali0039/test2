#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(void) {
clock_t sTime, eTime;
double cpu_time_used;

int myarray[100][100];
srand(0);
int i;
int j;
for (i = 0; i< 100; i++) {
  for (j = 0; j<100; j++) {
    myarray[i][j] = rand()%10 + 1;

  }
}

int temp;

sTime = clock();

for(i=0; i<100; i++){
  for(j=0; j<100; j++){
   myarray[i][j];
  }
}

eTime = clock();


cpu_time_used = ((double) (eTime - sTime)) / CLOCKS_PER_SEC;

printf("Subscript time: :%f\n",cpu_time_used);

int *p = NULL;

p = &myarray[0][0];

sTime = clock();
for (i = 0; i< 100; i++) {
  for (j = 0; j< 100; j++) {
    *(p + (i * j) + j);
  }
}


eTime = clock();


cpu_time_used = ((double) (eTime - sTime)) / CLOCKS_PER_SEC;

printf("Pointer time:%f\n",cpu_time_used);
return 0;
}
// Pointer references is faster and thus a bit more efficient than the subscript references. Subscripting is more reliable because it ensures we're accessing the correct element while pointers are hopping in the memory without checking if we're at the correct element and are able to go in a negative direction.


// int *p;

// sTime = clock();
// for (p = &myarray[0][0]; p <= &myarray[100-1][100 - 1]; p++) {
//  *p;
// }
