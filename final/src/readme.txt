========================================================================================================================
What data structure do you expect to have the best (fastest) performance?
========================================================================================================================
I thought it'd be avl.
In my implementation,
if it's bst, insert, do an inorder traversal and save those pairs into an array, then sort
if it's avl, insert (with rotation), do an inorder traversal and save those pairs into an array, then sort
if it's hash, insert, put all pairs in an array, sort twice (once according to key and once according to value)
Unless it's really bad bst or hash, the bottleneck will be sorting for all of them.
Reasons why I thought it'd be avl:
1) avl is a better version of bst because it's balanced, and balancing it shouldn't take long
2) hash needs to sort twice, which is sort of expensive
3) consider the case where we have really bad bst and hashing, insertion will become way more expensive

========================================================================================================================
Which one do you expect to be the slowest?
========================================================================================================================
bst
because if it somehow has a really long branch (which is totally possible)
insertion will take quite long (O(n^2) in the worst case)
it probably won't be hash because the implementation we have here rehashes
when it hits the load factor, so insertion will probably be theta(1) for each pair

========================================================================================================================
Do the results of timing your program’s execution match your expectations?
If so, briefly explain the correlation. 
If not, what run times deviated and briefly explain why you think this is the case.
========================================================================================================================
The output I got:
➜  src git:(main) ✗ time java CommonWordFinder lorem.txt bst 100 > bst_lorem.txt
java CommonWordFinder lorem.txt bst 100 > bst_lorem.txt  0.11s user 0.04s system 126% cpu 0.118 total
➜  src git:(main) ✗ time java CommonWordFinder lorem.txt avl 100 > avl_lorem.txt
java CommonWordFinder lorem.txt avl 100 > avl_lorem.txt  0.12s user 0.04s system 127% cpu 0.124 total
➜  src git:(main) ✗ time java CommonWordFinder lorem.txt hash 100 > hash_lorem.txt
java CommonWordFinder lorem.txt hash 100 > hash_lorem.txt  0.10s user 0.04s system 107% cpu 0.124 total
➜  src git:(main) ✗ time java CommonWordFinder Bible.txt bst 20000 > bst_bible.txt
java CommonWordFinder Bible.txt bst 20000 > bst_bible.txt  1.02s user 0.14s system 166% cpu 0.694 total
➜  src git:(main) ✗ time java CommonWordFinder Bible.txt avl 20000 > avl_bible.txt
java CommonWordFinder Bible.txt avl 20000 > avl_bible.txt  1.30s user 0.16s system 157% cpu 0.923 total
➜  src git:(main) ✗ time java CommonWordFinder Bible.txt hash 20000 > hash_bible.txt
java CommonWordFinder Bible.txt hash 20000 > hash_bible.txt  1.04s user 0.15s system 186% cpu 0.638 total

expectation: bst > hash > avl (time taken for large inputs)
result: avl > bst > hash (time taken for large inputs)

Turns out hash is really fast when the input text file is huge, and avl is the slowest :(
Possible explanations:
1. Arrays.sort() is much faster than O(nlgn)
thus sorting the array in hash twice won't really have a great impact on the overall performance
2. rotation in avl is more expensive than I'd imagined
technically it should be theta(1) for each rotation
but the time consumed piles up when rotations become frequent
3. bst is quite balanced
so we don't need to worry about the case when the tree branch is super long

Actually it turns out the order given above is not fixed either.
Sometimes avl will be quite fast, even faster than hash.
So my final comment on this is, choice of data structures is only part of the consideration,
there's so much more to consider: implementation, machine, etc.

Really enjoyed the course. Huge thanks to the teaching team, especially the TAs. You're amazing!

