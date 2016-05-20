//ESS_LOCALE English_UnitedStates.Latin1@Binary
/*
* function RANDOM(seed) 
*                    
* Version:1.0
* Date: Jan. 1, 2014
* Author: Donald Hagell
* Descrition:  Returns a random number from a distribution.
*                  
*/
SET CREATEBLOCKONEQ OFF;
SET CREATENONMISSINGBLK OFF;
SET FRMLBOTTOMUP OFF;
SET UPDATECALC OFF;
/*  com.astir.essbase.random.RandomVariables.seedEngine */
/* RUNJAVA com.astir.essbase.random.RandomVariables.RandomVariables(); */ /* Creates a new RNG with new Date() seed */
/* "Binomial" = @RandomVariablesRNGnextBinomial (2, 0.5); */
 /*"Raw" = @RandomVariablesRNGnextRaw();  */
/* Use a seed value for CONSTANT random variables*/
 "seed" = @RandomVariablesRNGseedEngine (32); 
/* "seed" = @RandomVariablesRNGseedEngine (@RandomVariablesRNGgetSeed ()); */
/* Gets the current seed from the RNG */
/*  "seed" = @RandomVariablesRNGgetSeed ("seed");  */

FIX(@RELATIVE("Period",0)); 
FIX(@RELATIVE("Scenario",0))
"Raw"(@CALCMODE (BLOCK);
"Raw" = @RandomVariablesRNGnextRaw (@CURRMBR("State"));
)
ENDFIX
FIX(@RELATIVE("Scenario",0))
"Double"(@CALCMODE (BLOCK);
"Double" = @RandomVariablesRNGnextDouble (@CURRMBR("State"));
)
ENDFIX
FIX(@RELATIVE("Scenario",0))
"Integer"(@CALCMODE (BLOCK);
"Integer" = @RandomVariablesRNGnextInt (@CURRMBR("State"));
)
ENDFIX
FIX(@RELATIVE("Scenario",0))
"Normal"(@CALCMODE (BLOCK);
"Normal" = @RandomVariablesRNGnextNormal (0.5,0.5,@CURRMBR("State"));
)
ENDFIX 
FIX(@RELATIVE("Scenario",0))
"Binomial"(@CALCMODE (BLOCK);
"Binomial" = @RandomVariablesRNGnextBinomial (1,0.5,@CURRMBR("State"));
"Coin Flip" = @RandomVariablesRNGnextBinomial (1,0.5,@CURRMBR("State"));
"Dice Roll" = @RandomVariablesRNGnextBinomial (12,(1/6),@CURRMBR("State")) ;
"Integer" = @RandomVariablesRNGnextBinomial (1,0.5,@CURRMBR("State"));
)
ENDFIX 
FIX(@RELATIVE("Scenario",0))
"GammaDist"(@CALCMODE (BLOCK);
"GammaDist" = @RandomVariablesRNGnextGamma (0.5,0.5,@CURRMBR("State"));
)
ENDFIX 
ENDFIX


