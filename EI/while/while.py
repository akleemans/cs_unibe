# Der untenstehende Python-Code stellt dieses Programm dar:
#
# (X0, X1 erhalten wir vom Benutzer und muessen nicht mehr definiert werden)
#
# X2 <- 0
#
# X3 <- X1
#
# while X0 != 0 do
#    X1 <- X3
#    while X1 != 0 do
#       X2 <- P(X2)
#       X1 <- S(X1)
#    od
#    X0 <- S(X0)
# od
# 
 
import sys

#Eingabe pruefen
if len(sys.argv) != 3: 
    print "Es werden exakt zwei Argumente erwartet. Breche ab."
    sys.exit()

x0 = int(sys.argv[1])
x1 = int(sys.argv[2])

#Resultat
x2 = 0

#Wert von x1 zwischenspeichern
x3 = x1

while x0 != 0:
	x1 = x3

	while x1 != 0:
		x2+=1
		x1-=1
	x0-=1

print x2
