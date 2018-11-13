package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
	int seedArrival;   // comment 1
	int seedItemA;   // comment 2
	int seedItemB;   // comment 3
	int seedItemCat;   // comment 4
	
	int seedPayMethodCat;
	int seedCheckWithCard;
	
	int seedScanTime;
	int seedPriceCheckTime;
	int seedPriceCheck;
	
	int seedCash;
	int seedCreditCard;
	int seedCheck;
	
	int seedAppTime;
	
	int seedBagTime;

	public Seeds(RandomSeedGenerator rsg)
	{
		seedArrival=rsg.nextSeed();
		seedItemA=rsg.nextSeed();
		seedItemB=rsg.nextSeed();
		seedItemCat=rsg.nextSeed();
		seedPayMethodCat=rsg.nextSeed();
		seedCheckWithCard=rsg.nextSeed();
		seedScanTime=rsg.nextSeed();
		seedPriceCheckTime=rsg.nextSeed();
		seedPriceCheck=rsg.nextSeed();
		seedCash=rsg.nextSeed();
		seedCreditCard=rsg.nextSeed();
		seedCheck=rsg.nextSeed();
		seedAppTime=rsg.nextSeed();
		seedBagTime=rsg.nextSeed();
	}
}
