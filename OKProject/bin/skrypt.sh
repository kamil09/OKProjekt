
czas=5
rozw=1

#GENEROWANIE INSTANCJI
while [ $rozw -le 100 ]
do
	java Main -TR0 ./TEST/$rozw.inst
	rozw=$[rozw+1]
done

#TESTY
printf "CZAS\t" > ./TEST/wyniki.txt
rozw=1
while [ $rozw -le 100 ]
do
	printf "Inst$rozw\t" >> ./TEST/wyniki.txt
	rozw=$[rozw+1]	
done
printf "\n" >> ./TEST/wyniki.txt


while [ $czas -le 120 ]
do
	printf "$(($czas/10)) \t" >> ./TEST/wyniki.txt
	
	rozw=1
	while [ $rozw -le 100 ]
	do
		java Main -T $(($czas/10)) -SF ./TEST/$rozw.inst >> ./TEST/wyniki.txt
		printf "\t" >> ./TEST/wyniki.txt
		rozw=$[rozw+1]	
	done
	printf "\n" >> ./TEST/wyniki.txt
	czas=$[czas+5]
done
