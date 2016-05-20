//ESS_LOCALE English_UnitedStates.Latin1@Binary
/*
* function RESET-RNG() reset the random number account members.
*                    
* Version:1.0
* Date: Jan. 1, 2014
* Author: Donald Hagell
* Descrition:  Reset values
*                  
*/
SET CREATEBLOCKONEQ OFF;
SET CREATENONMISSINGBLK OFF;
SET FRMLBOTTOMUP ON;
SET UPDATECALC OFF;
SET AGGMISSG ON ;

"seed" = #missing;
"Binomial" = #missing;
"Raw" = #missing;
"Double" = #missing;


CALC DIM ("Account");

