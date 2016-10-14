_Context

 Server implementation of a warehouse containing a set of items. The item quantity is added and removed
by clients in a SOA based system.

 The clients consume item quantities using named tasks.

 Clients can ask to be notified when a set of tasks is completed.


_Problem

 Handling thread concurrency of banks of memory.