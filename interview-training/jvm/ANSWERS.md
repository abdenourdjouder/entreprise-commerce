# JVM - Answers

## 1) Heap: objets vivants; metaspace: métadonnées de classes.
Heap = instances allouées à runtime. Metaspace = informations de classes et classloaders.  
En entretien, montre que tu sais diagnostiquer: `OutOfMemoryError: Java heap space` n’a pas les mêmes causes/remèdes que `OutOfMemoryError: Metaspace`.

## 2) ZGC cible des pauses très courtes sur gros heaps.
Argument senior: choix de GC = compromis latence/throughput/coût CPU.  
ZGC est pertinent quand le SLO de latence est strict; G1 reste souvent plus simple et robuste pour des workloads généraux.

## 3) Le JIT optimise après échauffement, sinon mesure biaisée.
Un benchmark “à froid” mélange coûts de chargement, compilation et warmup.  
Bonne réponse entretien: utiliser JMH, séparer warmup/measurement, puis confirmer par métriques de prod.

## 4) CPU, GC pauses, threads bloqués, pression mémoire.
Ordre de triage recommandé: saturation CPU, pauses GC, contention thread, puis pression mémoire/IO.  
Idée clé: une seule métrique isolée ne suffit pas; il faut corréler plusieurs signaux pour éviter les faux diagnostics.
