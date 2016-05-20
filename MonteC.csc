//ESS_LOCALE English_UnitedStates.Latin1@Binary
/*
* function Monty-Carlo(Udelta) returns a utility function
*                    
* Version:1.0
* Date: Jan. 1, 2014
* Author: Donald Hagell
* Description:  
*                  
*/
SET CREATEBLOCKONEQ OFF;
SET CREATENONMISSINGBLK OFF;
SET FRMLBOTTOMUP ON;
SET UPDATECALC OFF;
VAR stopIteration=0;
VAR maxIterations=74;
/* Monte Carlo calculation for Essbase*/
"seed" = @RandomVariablesRNGseedEngine (33); 
LOOP (100,stopIteration) 
FIX(@RELATIVE("k",0),@RELATIVE("Scenario",0))
		VAR aDiff=0;
		"GammaDist" ="Udelta";
		"delta"( @CALCMODE (BLOCK); 
					"Udelta" =   @RandomVariablesRNGnextGamma (0.5,0.5,@CURRMBR("State"));
					"delta" =  "Udelta" - "GammaDist" ;
			)
		"iterations"(
			"iterations" = "iterations" + 1;
			IF (("A"->"delta" >= "minDelta") OR ("iterations" >= maxIterations) ) stopIteration=1;
		)
ENDFIX



FIX("k0", "State", "None","Sprime",@RELATIVE("Scenario",0))
"iterations"(
		"iterations" = "iterations" + 1;
		IF (("A"->"delta" >= "minDelta") OR ("iterations" >= maxIterations) ) stopIteration=0;
)
ENDFIX 



ENDLOOP
/*
* Function: U(mdp, alpha, gamma;) 
*
* References:
*
*                  
*/