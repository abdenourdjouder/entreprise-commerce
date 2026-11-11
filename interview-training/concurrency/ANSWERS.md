# Concurrency - Answers

1. Non, il protège la structure, pas toutes les séquences métier.
2. Pour éviter les rafales synchronisées après panne.
3. En rendant l’aléatoire injectable et les assertions déterministes.
4. Le lock prévient conflit en amont, l’idempotence absorbe répétitions en aval.

