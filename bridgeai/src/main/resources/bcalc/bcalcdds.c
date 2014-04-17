#include "bcalcdds.h"
#include <stdio.h>

int main(int argc, char* argv[]) {
	if (argc != 2) {
		printf("Usage: bcalcdds <deal in PBN format>\n");
		return 0;
	}
	
	int SUIT_LEN = 5;
	int PLAYER_LEN = 4;
	int SUITS[]   = {BCALC_SUIT_CLUBS,   BCALC_SUIT_DIAMONDS, BCALC_SUIT_HEARTS,  BCALC_SUIT_SPADES, BCALC_NT};
	int PLAYERS[] = {BCALC_PLAYER_NORTH, BCALC_PLAYER_EAST,   BCALC_PLAYER_SOUTH, BCALC_PLAYER_WEST};

	// const char* deal = "N:AKQ982.J3.K874.9 J43.AK96.AQ96.Q8 T5.T8542.T3.6542 76.Q7.J52.AKJT73";
	char* deal = argv[1];
	printf("%s\n", deal);
	BCalcDDS* solver = bcalcDDS_new("PBN", deal, BCALC_SUIT_CLUBS, BCALC_PLAYER_EAST);
	if (solver == 0) {
		printf("Unable to allocate solver\n");
		return 1;
	}

	int tricks;
	const char* err;
	err = bcalcDDS_getLastError(solver);
	if (err != NULL) {
		printf("ERROR0: %s\n", err);
		bcalcDDS_clearError(solver);
	}

	for (int player = 0; player < PLAYER_LEN; player++) {
		bcalcDDS_setPlayerOnLeadAndReset(solver, PLAYERS[player]);
		err = bcalcDDS_getLastError(solver);
		if (err != NULL) {
			printf("ERROR1: %s\n", err);
			bcalcDDS_clearError(solver);
		}

		for (int suit = 0; suit < SUIT_LEN; suit++) {
			bcalcDDS_setTrumpAndReset(solver, SUITS[suit]);
			err = bcalcDDS_getLastError(solver);
			if (err != NULL) {
				printf("ERROR2: %s\n", err);
				bcalcDDS_clearError(solver);
			}

			tricks = bcalcDDS_getTricksToTake(solver);
			err = bcalcDDS_getLastError(solver);
			if (err != NULL) {
				printf("ERROR3: %s\n", err);
				bcalcDDS_clearError(solver);
			}

			printf("%d %c %c\n", tricks, bcalc_suitSymbol(SUITS[suit]), bcalc_playerSymbol(PLAYERS[player]));
		}
	}
}