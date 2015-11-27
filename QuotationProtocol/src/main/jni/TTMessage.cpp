#include "TTMessage.h"
#include <memory.h>
#include <android/log.h>
std::map<std::string,FieldInfo*> message2::alldef_field_name;
std::map<int,FieldInfo*> message2::alldef_field_tag;
std::map<std::string , FieldType> message2::name_types_ ;
int message2::allfieldsize[TTFIELDNUM];
void message2::Init()
{
	InitNameType();
/*	DEFINE_FIELD(  EndDateTime , long) ;
	DEFINE_FIELD(  Market		,string );
	DEFINE_FIELD(  SecurityID , string) ;
	DEFINE_FIELD(  AbbrName , string) ;
	DEFINE_FIELD(  DateTime		, long );
	DEFINE_FIELD(  LatestPx	 , 	double);
	DEFINE_FIELD(  BidPx , double) ;
	DEFINE_FIELD(  AskPx	,double );
	DEFINE_FIELD(  OpenPx	 , 	double);
	DEFINE_FIELD(  HighPx		,double );
	DEFINE_FIELD(  LowPx		,double );
	DEFINE_FIELD(  PrevClosePx		,double );	
	DEFINE_FIELD(  TotalTradeVolume,double );
	DEFINE_FIELD(  TotalTradeAmount,double );
	DEFINE_FIELD(  PreOpenPosition , double) ;
	DEFINE_FIELD(  	BidPx1 , double) ;
	DEFINE_FIELD(  BidVolume1		,double );
	DEFINE_FIELD(  	BidPx2 , double) ;
	DEFINE_FIELD(  BidVolume2		,double );
	DEFINE_FIELD(  	BidPx3	, double) ;
	DEFINE_FIELD(  BidVolume3		,double );
	DEFINE_FIELD(  	BidPx4 , double) ;
	DEFINE_FIELD(  BidVolume4		,double );
	DEFINE_FIELD(  	BidPx5 , double) ;
	DEFINE_FIELD(  BidVolume5		,double );
	DEFINE_FIELD(  AskPx1	,double );
	DEFINE_FIELD(  AskVolume1		,double );
	DEFINE_FIELD(  AskPx2	,double );
	DEFINE_FIELD(  AskVolume2		,double );
	DEFINE_FIELD(  AskPx3	,double );
	DEFINE_FIELD(  AskVolume3		,double );
	DEFINE_FIELD(  AskPx4	,double );
	DEFINE_FIELD(  AskVolume4		,double );
	DEFINE_FIELD(  AskPx5	,double );
	DEFINE_FIELD(  AskVolume5		,double );
	DEFINE_FIELD(  PreSettlementPx , double) ;

	DEFINE_FIELD(  MSGSEQ , unsigned int) ;
	DEFINE_FIELD(  StringField , string) ;
	DEFINE_FIELD(  BoolField ,bool) ;
	DEFINE_FIELD(  DateTimeField , timestamp) ;
	DEFINE_FIELD(  I8Field ,  char) ;
	DEFINE_FIELD(  U8Field ,  unsigned char) ;
	DEFINE_FIELD(  I16Field , short) ;
	DEFINE_FIELD(  U16Field , unsigned short) ;
	DEFINE_FIELD(  I32Field , int) ;
	DEFINE_FIELD(  U32Field , unsigned int) ;
	DEFINE_FIELD(  I64Field	, long) ;
	DEFINE_FIELD(  U64Field	, unsigned long) ;
	DEFINE_FIELD(  F32Field	, float) ;
	DEFINE_FIELD(  F64Field	, double) ;
	//DEFINE_FIELD(  DATA     , string) ;
       //DEFINE_FIELD(  SecureData       , rawdata) ;*/
	DEFINE_FIELD(TestReqID , string) ;
	DEFINE_FIELD(EncryptMethod , int) ;
	DEFINE_FIELD(HeartBtInt , int) ;
	DEFINE_FIELD(DefaultApplVerID , string) ;
	DEFINE_FIELD(SecurityExchange , string) ;
	DEFINE_FIELD(SecurityID , string) ;
	DEFINE_FIELD(SecurityCode , unsigned int) ;
	DEFINE_FIELD(QuoteSeqID , unsigned int) ;
	DEFINE_FIELD(NoMDEntries , unsigned int) ;
	DEFINE_FIELD(NoInstrument , unsigned int) ;
	DEFINE_FIELD(NoSubject , unsigned int) ;
	DEFINE_FIELD(NewsID , long) ;
	DEFINE_FIELD(Headline , string) ;
	DEFINE_FIELD(URLLink , string) ;
	DEFINE_FIELD(Summary , string) ;
	DEFINE_FIELD(Token , string) ;
	DEFINE_FIELD(MDEntryType , unsigned char) ;
	DEFINE_FIELD(AlertType , char) ;
	DEFINE_FIELD(SubjectName , string) ;
	DEFINE_FIELD(SubjectCode , unsigned int) ;
	DEFINE_FIELD(IsIndexLine , bool) ;
	DEFINE_FIELD(Origin , string) ;
	DEFINE_FIELD(OrigTime , long) ;
	DEFINE_FIELD(FilterRuleID , long) ;
	DEFINE_FIELD(ChannelType , int) ;
	DEFINE_FIELD(Board , string) ;


	DEFINE_FIELD(ListMarketID , 	string);
	DEFINE_FIELD(MarketTradingTarget  ,  string);
	DEFINE_FIELD(Symbol , string) ;
	DEFINE_FIELD(BidTime , long);
	DEFINE_FIELD(BidPx , double) ;
	DEFINE_FIELD(BidSize , double) ;
	DEFINE_FIELD(BidContraRate	 , 	double);
	DEFINE_FIELD(BidSettlmntTyp , 	unsigned int);
	DEFINE_FIELD(BidFlegStlmntMthd , unsigned int);
	DEFINE_FIELD(OfferContraRate	 , 	double);
	DEFINE_FIELD(OfferPx , double) ;
	DEFINE_FIELD(OfferSize ,double) ;
	DEFINE_FIELD(OfferSettlmntTyp ,  unsigned int);
	DEFINE_FIELD(OfferFlegStlmntMthd , unsigned int);
	DEFINE_FIELD(MDEntryDateTime , long);
	DEFINE_FIELD(TradeType , string) ;
	DEFINE_FIELD(Chge	 , 	double);
	DEFINE_FIELD(ChgeRate	 , 	double);
	DEFINE_FIELD(TotalTradedAmnt ,   double);
	DEFINE_FIELD(TradedAmnt , 	double);
	DEFINE_FIELD(TradeQuantity , 	unsigned int);
	DEFINE_FIELD(LatestPx	 , 	double);
	DEFINE_FIELD(OpenPx	 , 	double);
	DEFINE_FIELD(ClosePx	 , 	double);
	DEFINE_FIELD(HighstPx	 , 	double);
	DEFINE_FIELD(LwstPx	 , 	double);
	DEFINE_FIELD(WghtdPx	 , 	double);
	DEFINE_FIELD(PreClosePx , 	double);
	DEFINE_FIELD(PreWghtdPx , 	double);
	DEFINE_FIELD(LatestContraRate , 	double);
	DEFINE_FIELD(OpenContraRate , 	double);
	DEFINE_FIELD(CloseContraRate , 	double);
	DEFINE_FIELD(HighstContraRate , 	double);
	DEFINE_FIELD(LwstContraRate , 	double);
	DEFINE_FIELD(WghtdContraRate , 	double);
	DEFINE_FIELD(PreCloseContraRate , double);
	DEFINE_FIELD(PreWghtdContraRate , double);
	DEFINE_FIELD(EntySideNo ,		unsigned int) ;
	DEFINE_FIELD(Command , string) ;
	DEFINE_FIELD(ResultInfo , string) ;
	DEFINE_FIELD(ResultPageNo , unsigned int) ;
	DEFINE_FIELD(ResultPageEnd , bool) ;

	DEFINE_FIELD(TotalVolumeTraded , double) ;
	DEFINE_FIELD(	TotalValueTraded ,double) ;
	DEFINE_FIELD(	NumberOfTrades	,unsigned int) ;
	DEFINE_FIELD(	PERatio1 ,double) ;
	DEFINE_FIELD(	PERatio2 , double) ;
	DEFINE_FIELD(	Change1	, double) ;
	DEFINE_FIELD(	Change2	, double) ;
	DEFINE_FIELD(	OpenPosition , unsigned long) ;
	DEFINE_FIELD(	OfferPx5 , double) ;
	DEFINE_FIELD(	OfferSize5 ,double) ;
	DEFINE_FIELD(	OfferPx4 , double) ;
	DEFINE_FIELD(	OfferSize4 , double) ;
	DEFINE_FIELD(	OfferPx3 , double) ;
	DEFINE_FIELD(	OfferSize3 , double) ;
	DEFINE_FIELD(	OfferPx2 , double) ;
	DEFINE_FIELD(	OfferSize2 , double) ;
	DEFINE_FIELD(	OfferPx1 , double) ;
	DEFINE_FIELD(	OfferSize1 , double) ;
	DEFINE_FIELD(	BidPx5 , double) ;
	DEFINE_FIELD(	BidSize5 , double) ;
	DEFINE_FIELD(	BidPx4 , double) ;
	DEFINE_FIELD(	BidSize4 , double) ;
	DEFINE_FIELD(	BidPx3	, double) ;
	DEFINE_FIELD(	BidSize3 , double) ;
	DEFINE_FIELD(	BidPx2 , double) ;
	DEFINE_FIELD(	BidSize2 , double) ;
	DEFINE_FIELD(	BidPx1 , double) ;
	DEFINE_FIELD(	BidSize1 , double) ;
	DEFINE_FIELD( MessageType , int) ;
	DEFINE_FIELD(TradedVolume , double) ;
	DEFINE_FIELD(TradedValue , double) ;

	DEFINE_FIELD(BidQuoteSide , string) ;
	DEFINE_FIELD(BidDirtyPx , double) ;
	DEFINE_FIELD(OfferQuoteSide , string) ;
	DEFINE_FIELD(OfferQuoteTime , long) ;
	DEFINE_FIELD(OfferDirtyPx , double) ;
	DEFINE_FIELD(AccruedInterest , double) ;
	DEFINE_FIELD(UpdateTime , long) ;
	DEFINE_FIELD(UpdateMillisec , unsigned int) ;
	DEFINE_FIELD(SettlementGroupID , string) ;
	DEFINE_FIELD(SettlementID , unsigned int) ;
	DEFINE_FIELD(PreSettlementPx , double) ;
	DEFINE_FIELD(PreOpenPosition , double) ;
	DEFINE_FIELD(SettlementPx , double) ;
	DEFINE_FIELD(UpperLimitPx , double) ;
	DEFINE_FIELD(LowerLimitPx , double) ;
	DEFINE_FIELD(PreDelta , double) ;
	DEFINE_FIELD(CurrDelta , double) ;

	DEFINE_FIELD(RequestID		, int);
	DEFINE_FIELD(StartTime		, int);
	DEFINE_FIELD(MinuteSpan		, char);
	DEFINE_FIELD(TotalMessage		, unsigned int);
	DEFINE_FIELD(Sequence			, unsigned int);
	DEFINE_FIELD(AvgPx			, double);
	DEFINE_FIELD(RequestResult	, string);
	DEFINE_FIELD(GroupExpr		,	string);
	DEFINE_FIELD(FieldName		,	string);
	DEFINE_FIELD(SortBegin		,	int);
	DEFINE_FIELD(SortSize			,	int);
	DEFINE_FIELD(SortRespType		,	char);
	DEFINE_FIELD(ListCount		,	unsigned int);
	DEFINE_FIELD(GroupID			,	unsigned int);
	DEFINE_FIELD(AscOrder			,	bool);

	DEFINE_FIELD(BidContraRate1 , double);
	DEFINE_FIELD(BidContraRate2 , double);
	DEFINE_FIELD(BidContraRate3 , double);
	DEFINE_FIELD(BidContraRate4 , double);
	DEFINE_FIELD(BidContraRate5 , double);

	DEFINE_FIELD(OfferContraRate1 , double);
	DEFINE_FIELD(OfferContraRate2 , double);
	DEFINE_FIELD(OfferContraRate3 , double);
	DEFINE_FIELD(OfferContraRate4 , double);
	DEFINE_FIELD(OfferContraRate5 , double);
	DEFINE_FIELD(ID				, unsigned int) ;
	DEFINE_FIELD(OpenClosedSign	, unsigned int) ;
	DEFINE_FIELD(Keywords	, string) ;
	DEFINE_FIELD(TTPreClosePx , 	double);
	DEFINE_FIELD(TTPreCloseContraRate , 	double);

	DEFINE_FIELD(BidMinQty1 , double);
	DEFINE_FIELD(BidMinQty2 , double);
	DEFINE_FIELD(BidMinQty3 , double);
	DEFINE_FIELD(BidMinQty4 , double);
	DEFINE_FIELD(BidMinQty5 , double);

	DEFINE_FIELD(OfferMinQty1 , double);
	DEFINE_FIELD(OfferMinQty2 , double);
	DEFINE_FIELD(OfferMinQty3 , double);
	DEFINE_FIELD(OfferMinQty4 , double);
	DEFINE_FIELD(OfferMinQty5 , double);

	DEFINE_FIELD(BidQuoteSide1 , string) ;
	DEFINE_FIELD(BidQuoteSide2 , string) ;
	DEFINE_FIELD(BidQuoteSide3 , string) ;
	DEFINE_FIELD(BidQuoteSide4 , string) ;
	DEFINE_FIELD(BidQuoteSide5 , string) ;
	DEFINE_FIELD(OfferQuoteSide1 , string) ;
	DEFINE_FIELD(OfferQuoteSide2 , string) ;
	DEFINE_FIELD(OfferQuoteSide3 , string) ;
	DEFINE_FIELD(OfferQuoteSide4 , string) ;
	DEFINE_FIELD(OfferQuoteSide5 , string) ;

	DEFINE_FIELD(BidTime1 , long) ;
	DEFINE_FIELD(BidTime2 , long) ;
	DEFINE_FIELD(BidTime3 , long) ;
	DEFINE_FIELD(BidTime4 , long) ;
	DEFINE_FIELD(BidTime5 , long) ;
	DEFINE_FIELD(OfferQuoteTime1 , long) ;
	DEFINE_FIELD(OfferQuoteTime2 , long) ;
	DEFINE_FIELD(OfferQuoteTime3 , long) ;
	DEFINE_FIELD(OfferQuoteTime4 , long) ;
	DEFINE_FIELD(OfferQuoteTime5 , long) ;

	DEFINE_FIELD(FxType	,		int) ;
	DEFINE_FIELD(TradeCode	,	string) ;
	DEFINE_FIELD(Millisec	,		unsigned int) ;
	DEFINE_FIELD(PreAvrgPx	,	double) ;
	DEFINE_FIELD(MidPx	,	double) ;
	DEFINE_FIELD(LatestYield	,	double) ;
	DEFINE_FIELD(ChangeBP	,	double) ;

	DEFINE_FIELD(DateTime		, long );
	DEFINE_FIELD(CloseYield		,double );
	DEFINE_FIELD(OpenYield		,double );
	DEFINE_FIELD(HighYield		,double );
	DEFINE_FIELD(LowYield		,double );
	DEFINE_FIELD(WghtdYield		,double );
	DEFINE_FIELD(PrevCloseYield	,double );
	DEFINE_FIELD(PrevWghtdYield	,double );

	DEFINE_FIELD(HighPx		,double );
	DEFINE_FIELD(LowPx		,double );
	DEFINE_FIELD(PrevClosePx		,double );	
	DEFINE_FIELD(PrevWghtdPx		,double );

	DEFINE_FIELD(Change			,double );
	DEFINE_FIELD(ChangeRate		,double );
	DEFINE_FIELD(YieldChange		,double );
	DEFINE_FIELD(TradeVolume		,double );
	DEFINE_FIELD(TradeAmount		,double );
	DEFINE_FIELD(TotalTradeVolume,double );
	DEFINE_FIELD(TotalTradeAmount,double );
	DEFINE_FIELD(TurnOverRate	,double );

	DEFINE_FIELD(MacaulayDuration,double );
	DEFINE_FIELD(ModifiedDuration,double );
	DEFINE_FIELD(Convexity		,double );
	DEFINE_FIELD(DV01			,double );
	DEFINE_FIELD(Market		,string );

	DEFINE_FIELD(BidYield		,double );
	DEFINE_FIELD(BidVolume		,double );
	DEFINE_FIELD(BidMarketMaker	,string );
	DEFINE_FIELD(BidSettlType	,string );
	DEFINE_FIELD(AskYield		,double );
	DEFINE_FIELD(AskVolume		,double );
	DEFINE_FIELD(AskMarketMaker	,string );
	DEFINE_FIELD(AskSettlType	,string );
	DEFINE_FIELD(AskPx	,double );
	DEFINE_FIELD(AskTime	,long );

	DEFINE_FIELD(BidYield1		,double );
	DEFINE_FIELD(BidVolume1		,double );
	DEFINE_FIELD(BidMarketMaker1	,string );
	DEFINE_FIELD(BidSettlType1	,string );
	DEFINE_FIELD(AskYield1		,double );
	DEFINE_FIELD(AskVolume1		,double );
	DEFINE_FIELD(AskMarketMaker1	,string );
	DEFINE_FIELD(AskSettlType1	,string );
	DEFINE_FIELD(AskPx1	,double );
	DEFINE_FIELD(AskTime1	,long );

	DEFINE_FIELD(BidYield2		,double );
	DEFINE_FIELD(BidVolume2		,double );
	DEFINE_FIELD(BidMarketMaker2	,string );
	DEFINE_FIELD(BidSettlType2	,string );
	DEFINE_FIELD(AskYield2		,double );
	DEFINE_FIELD(AskVolume2		,double );
	DEFINE_FIELD(AskMarketMaker2	,string );
	DEFINE_FIELD(AskSettlType2	,string );
	DEFINE_FIELD(AskPx2	,double );
	DEFINE_FIELD(AskTime2	,long );

	DEFINE_FIELD(BidYield3		,double );
	DEFINE_FIELD(BidVolume3		,double );
	DEFINE_FIELD(BidMarketMaker3	,string );
	DEFINE_FIELD(BidSettlType3	,string );
	DEFINE_FIELD(AskYield3		,double );
	DEFINE_FIELD(AskVolume3		,double );
	DEFINE_FIELD(AskMarketMaker3	,string );
	DEFINE_FIELD(AskSettlType3	,string );
	DEFINE_FIELD(AskPx3	,double );
	DEFINE_FIELD(AskTime3	,int64 );

	DEFINE_FIELD(BidYield4		,double );
	DEFINE_FIELD(BidVolume4		,double );
	DEFINE_FIELD(BidMarketMaker4	,string );
	DEFINE_FIELD(BidSettlType4	,string );
	DEFINE_FIELD(AskYield4		,double );
	DEFINE_FIELD(AskVolume4		,double );
	DEFINE_FIELD(AskMarketMaker4	,string );
	DEFINE_FIELD(AskSettlType4	,string );
	DEFINE_FIELD(AskPx4	,double );
	DEFINE_FIELD(AskTime4	,long );

	DEFINE_FIELD(BidYield5		,double );
	DEFINE_FIELD(BidVolume5		,double );
	DEFINE_FIELD(BidMarketMaker5	,string );
	DEFINE_FIELD(BidSettlType5	,string );
	DEFINE_FIELD(AskYield5		,double );
	DEFINE_FIELD(AskVolume5		,double );
	DEFINE_FIELD(AskMarketMaker5	,string );
	DEFINE_FIELD(AskSettlType5	,string );
	DEFINE_FIELD(AskPx5	,double );
	DEFINE_FIELD(AskTime5	,long );

	DEFINE_FIELD(QuoteSpread		,double );
	DEFINE_FIELD(NumOfQuote		,unsigned int );
	DEFINE_FIELD(BidTotalVolume	,double );
	DEFINE_FIELD(AskTotalVolume	,double );
	DEFINE_FIELD(NumOfTrades		,unsigned int) ;
	DEFINE_FIELD(PERatio			,double) ;
	DEFINE_FIELD(Snapshot			,int) ;
	DEFINE_FIELD(RequestResultCode			,int) ;

	DEFINE_FIELD(FilterExpr		,string );
	DEFINE_FIELD(Begin	,int );
	DEFINE_FIELD(Size	,int );

	DEFINE_FIELD(AbbrName , string) ;
	DEFINE_FIELD(YearsToMaturity , double) ;
	DEFINE_FIELD(DatedDate , long) ;
	DEFINE_FIELD(MaturityDate , long) ;
	DEFINE_FIELD(CouponRate , double) ;
	DEFINE_FIELD(IssueVolume , double) ;
	DEFINE_FIELD(BondCategory , string) ;

	DEFINE_FIELD(BondCreditRating , string) ;
	DEFINE_FIELD(BondCreditRatingAgency , string) ;
	DEFINE_FIELD(CorporateCreditRating , string) ;
	DEFINE_FIELD(CorporateCreditRatingAgency , string) ;
	DEFINE_FIELD(CDCYieldInTheory , double) ;
	DEFINE_FIELD(CDCPxInTheory , double) ;
	DEFINE_FIELD(BeginDateTime , long) ;
	DEFINE_FIELD(EndDateTime , long) ;
	DEFINE_FIELD(TTPrevClosePx , double) ;
	DEFINE_FIELD(TTPrevCloseYield , double) ;
	DEFINE_FIELD(PositionChange, double) ;
	DEFINE_FIELD(TotalPositionChange, double) ;
	DEFINE_FIELD(SecurityType, string);
	DEFINE_FIELD(OffSet, int);
	DEFINE_FIELD(ErrMsg, string);
	DEFINE_FIELD(InternalCode , int) ;

	DEFINE_FIELD(MSGSEQ , unsigned int) ;
	DEFINE_FIELD(StringField , string) ;
	DEFINE_FIELD(BoolField ,bool) ;
	DEFINE_FIELD(DateTimeField , timestamp) ;
	DEFINE_FIELD(I8Field ,  char) ;
	DEFINE_FIELD(U8Field ,  unsigned char) ;
	DEFINE_FIELD(I16Field , short) ;
	DEFINE_FIELD(U16Field , unsigned short) ;
	DEFINE_FIELD(I32Field , int) ;
	DEFINE_FIELD(U32Field , unsigned int) ;
	DEFINE_FIELD(I64Field	, long) ;
	DEFINE_FIELD(U64Field	, unsigned long) ;
	DEFINE_FIELD(F32Field	, float) ;
	DEFINE_FIELD(F64Field	, double) ;
	DEFINE_FIELD(DATA , string) ;
	DEFINE_FIELD(DEBUGINFO , string) ;
	DEFINE_FIELD(Username , string) ;
	DEFINE_FIELD(Password , string) ;

	DEFINE_FIELD(SecureDataLen	, int) ;
	DEFINE_FIELD(SecureData	, rawdata) ;
	DEFINE_FIELD(CLIENT_TYPE , int) ;
	DEFINE_FIELD(CLIENT_VERSION , string) ;
	DEFINE_FIELD(Data_Sequence	, unsigned long) ;
	DEFINE_FIELD(MsgType,int) ;
	DEFINE_FIELD(TradeDate,string);
	DEFINE_FIELD(LastUpdateTime,string);	


	//string
	DEFINE_FIELD(ReserveString_1 , string) ;
	DEFINE_FIELD(ReserveString_2 , string) ;
	DEFINE_FIELD(ReserveString_3 , string) ;
	DEFINE_FIELD(ReserveString_4 , string) ;
	DEFINE_FIELD(ReserveString_5 , string) ;
	DEFINE_FIELD(ReserveString_6 , string) ;
	DEFINE_FIELD(ReserveString_7 , string) ;
	DEFINE_FIELD(ReserveString_8 , string) ;
	DEFINE_FIELD(ReserveString_9 , string) ;
	DEFINE_FIELD(ReserveString_10 , string) ;

	//int32 
	DEFINE_FIELD(ReserveInt32_1 , int) ;
	DEFINE_FIELD(ReserveInt32_2 , int) ;
	DEFINE_FIELD(ReserveInt32_3 , int) ;
	DEFINE_FIELD(ReserveInt32_4 , int) ;
	DEFINE_FIELD(ReserveInt32_5 , int) ;
	DEFINE_FIELD(ReserveInt32_6 , int) ;
	DEFINE_FIELD(ReserveInt32_7 , int) ;
	DEFINE_FIELD(ReserveInt32_8 , int) ;
	DEFINE_FIELD(ReserveInt32_9 , int) ;
	DEFINE_FIELD(ReserveInt32_10 , int) ;

	//int64

	DEFINE_FIELD(ReserveInt64_1 , long) ;
	DEFINE_FIELD(ReserveInt64_2 , long) ;
	DEFINE_FIELD(ReserveInt64_3 , long) ;
	DEFINE_FIELD(ReserveInt64_4 , long) ;
	DEFINE_FIELD(ReserveInt64_5 , long) ;
	DEFINE_FIELD(ReserveInt64_6 , long) ;
	DEFINE_FIELD(ReserveInt64_7 , long) ;
	DEFINE_FIELD(ReserveInt64_8 , long) ;
	DEFINE_FIELD(ReserveInt64_9 , long) ;
	DEFINE_FIELD(ReserveInt64_10 , long) ;

	//double

	DEFINE_FIELD(ReserveDouble_1 , double) ;
	DEFINE_FIELD(ReserveDouble_2 , double) ;
	DEFINE_FIELD(ReserveDouble_3 , double) ;
	DEFINE_FIELD(ReserveDouble_4 , double) ;
	DEFINE_FIELD(ReserveDouble_5 , double) ;
	DEFINE_FIELD(ReserveDouble_6 , double) ;
	DEFINE_FIELD(ReserveDouble_7 , double) ;
	DEFINE_FIELD(ReserveDouble_8 , double) ;
	DEFINE_FIELD(ReserveDouble_9 , double) ;
	DEFINE_FIELD(ReserveDouble_10 , double) ;
}
FieldType message2::name_to_type(const std::string& name)
{
	std::map<std::string , FieldType>::iterator citer = name_types_.find(name);
	if(citer == name_types_.end())
		return TTUNTYPE ;
	else
		return citer->second ;
}
void message2::InitNameType()
{
	name_types_["double"] = TTDOUBLE ;
	name_types_["float"] = TTFLOAT ;
	name_types_["char"] = TTINT8 ;
	name_types_["unsigned char"] = TTUINT8 ;
	name_types_["short"] = TTINT16 ;
	name_types_["unsigned short"] = TTUINT16 ;
	name_types_["int"] = TTINT32 ;
	name_types_["unsigned int"] = TTUINT32 ;
	name_types_["long"] = TTINT64 ;
	name_types_["unsigned long"] = TTUINT64 ;
	name_types_["string"] = TTSTRING ;
	name_types_["bool"] = TTBOOL ;
	name_types_["rawdata"] = TTRAWDATA ;
	name_types_["timestamp"] = TTTIMESTAMP ;		

	allfieldsize[TTUNTYPE] = 0; 
	allfieldsize[TTINT8] = sizeof(char); 
	allfieldsize[TTUINT8] = sizeof(unsigned char); 
	allfieldsize[TTUINT16] = sizeof(unsigned short); 
	allfieldsize[TTINT16] = sizeof(short); 
	allfieldsize[TTUINT32] = sizeof(unsigned int); 
	allfieldsize[TTINT32] = sizeof(int); 
	allfieldsize[TTUINT64] = 8; 
	allfieldsize[TTINT64] = 8; 
	allfieldsize[TTFLOAT] = sizeof(float); 
	allfieldsize[TTDOUBLE] = sizeof(double); 
	allfieldsize[TTBOOL] = sizeof(bool); 
	allfieldsize[TTTIMESTAMP] = 8; 
	allfieldsize[TTSTRING] = 0; 
	allfieldsize[TTRAWDATA] = 0; 
}
void message2::AddField(const int tag,const char* name,FieldType type)
{
	FieldInfo* p = new FieldInfo;
	p->name = name;
	p->tag = tag;
	p->type = type;
	alldef_field_tag[tag] = p;
	alldef_field_name[name] = p; 	
}
void message2::UnInit()
{
	for(std::map<std::string,FieldInfo*>::iterator it = alldef_field_name.begin();it != alldef_field_name.end();it++)
		delete it->second;
	alldef_field_name.clear();
	alldef_field_tag.clear();
}
void message2::AddHead(bool bAddNetHead)
{
	if(bAddNetHead == true)
	{
		char sbuf[2];
		sbuf[0] = 85 ;
		sbuf[1] = 85 ;
		data->append(sbuf,2);
		unsigned short datalen = 0;
		data->append((const char*)&datalen,sizeof(datalen));
	}
	char msg_phead[4];
	unsigned short msg_data_len = 2 + (subject_.length() + 1) + sizeof(int) + (data->length()-start_pos);
	memcpy(msg_phead , &msg_data_len , 2) ;
	msg_phead[2] = get_msg_type() ;
	msg_phead[3] = MESSAGE_MODE_RAW ;
	data->append(msg_phead,4);
	data->append(subject_.data(),subject_.length());
	*data += '\0';
	int code_ = 0;
	data->append((const char*)&code_,sizeof(code_));
}
message2::message2():outer(NULL),data(NULL),start_pos(0),bAddNetHead(false),type_(0),code_(1)
{
	data = &inner;
	AddHead(bAddNetHead);
}
message2::message2(std::string* out,bool bAddNetHead_):outer(out),data(NULL),start_pos(0),bAddNetHead(bAddNetHead_),type_(0),code_(1)
{
	if(outer != NULL)
	{
		data = outer;
		start_pos = outer->length();
	}
	else
		data = &inner;
	AddHead(bAddNetHead);
}
message2::message2(const message2 & msg)
{
	type_ = msg.type_;
	code_ = msg.code_;
	allfield = msg.allfield ;
	outer = NULL;
	inner.assign((msg.data->data()+msg.start_pos),(msg.data->length()-msg.start_pos));
	start_pos = 0;
	bAddNetHead = msg.bAddNetHead;
	data = &inner;
}
message2& message2::operator=(const message2& msg)
{
	type_ = msg.type_;
	allfield = msg.allfield ;
	code_ = msg.code_;
	bAddNetHead = msg.bAddNetHead;
	if(outer != NULL)
	{
		outer->append((msg.data->data()+msg.start_pos),(msg.data->length()-msg.start_pos));
		data = outer;
	}
	else
	{
		inner.assign((msg.data->data()+msg.start_pos),(msg.data->length()-msg.start_pos));
		start_pos = 0;
		data = &inner;
	}
	return (*this) ;
}
message2::~message2()
{
}
const char* message2::getdata()
{
	return (const char*)(data->data()+start_pos);
}
int message2::length()
{
	return (data->length()-start_pos);
}
void message2::reset()
{
	data->erase(start_pos,length()-start_pos);
	subject_.clear();
	type_ = 0;
	code_ = 1;
	allfield.clear();
	AddHead(bAddNetHead);
}
void message2::reused()
{
	int datapos = start_pos + 4 +  (subject_.length() + 1) + sizeof(int);
	if(bAddNetHead == true)
		datapos += 4;
	data->erase(datapos,length()-datapos);
	allfield.clear();
}
unsigned char message2::get_msg_type() const
{
	return type_ ;
}
void message2::set_msg_type(unsigned char type)
{
	type_ = type ;
	int pos = start_pos + 2;
	if(bAddNetHead == true)
		pos += 4;
	memcpy((char*)(data->data() + pos),&type_,sizeof(unsigned char));
}
int message2::get_msg_code() const 
{
	return code_;
}
void message2::set_msg_code(int code) 
{
	code_ = code;
	int pos = start_pos + 4 +  (subject_.length() + 1);
	if(bAddNetHead == true)
		pos += 4;
	memcpy((char*)(data->data() + pos),&code_,sizeof(int));
}
void message2::flush()
{
	if(bAddNetHead == true)
	{
		unsigned short datalen = data->length() - start_pos - 4;
		memcpy((char*)(data->data() + start_pos + 2),&datalen,sizeof(datalen));
		unsigned short msg_len = datalen - 2;
		memcpy((char*)(data->data() + start_pos + 4),&msg_len,sizeof(msg_len));
	}
	else
	{
		if(start_pos != 0)
		{
			unsigned short datalen = data->length() - 4;
			memcpy((char*)(data->data() + 2),&datalen,sizeof(datalen));
		}
		unsigned short msg_len = data->length() - start_pos - 2;
		memcpy((char*)(data->data() + start_pos),&msg_len,sizeof(msg_len));
	}
}
int message2::get_field_count() const
{
	return allfield.size() ;
}
const std::string& message2::get_subject() const
{
	return subject_ ;
}
void message2::set_subject(const std::string& subject)
{
	int pos = start_pos + 4;
	if(bAddNetHead == true)
		pos += 4;
	data->insert(pos,subject.data(),subject.length());
	data->erase(pos+subject.length(),subject_.length());
	subject_ = subject ;
}
bool message2::add_field_int8(const std::string& name , char value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTINT8))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_uint8(const std::string& name , unsigned char value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTUINT8))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_int16(const std::string& name , short value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTINT16))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_uint16(const std::string& name , unsigned short value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTUINT16))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_int32(const std::string& name , int value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTINT32))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_uint32(const std::string& name , unsigned int value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTUINT32))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_int64(const std::string& name , long value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTINT64))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_uint64(const std::string& name , unsigned long value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTUINT64))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_float(const std::string& name , float value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTFLOAT))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),sizeof(float));
	return true;
}

bool message2::add_field_double(const std::string& name , double value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTDOUBLE))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}

bool message2::add_field_bool(const std::string& name , bool value)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTBOOL))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&value),allfieldsize[m.type]);
	return true;
}
bool message2::add_field_string(const std::string& name ,const std::string& value)
{
	return add_field_string(name,value.c_str(),value.length());
}
bool message2::add_field_string(const std::string& name , const char * value,unsigned short str_len)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTSTRING))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	unsigned short len = strlen(value);
	if(str_len != 0)
		len  = str_len;
	if(len < 4)
	{
		unsigned short newlen = 4;
		data->append((const char*)(&newlen),2);
		data->append(value,len);
		for(int i=0;i<(newlen-len);i++)
		{
			*data += '\0';
		}
	}
	else
	{
		unsigned short newlen = len + 1;
		data->append((const char*)(&newlen),2);
		data->append(value,len);
		*data += '\0';
	}
	return true;
}
bool message2::add_field_raw(const std::string& name , const char * value,unsigned short len)
{
	fielddefine m;
	if(false == AddFieldDefine(m,name,TTRAWDATA))
		return false ;
	data->append((const char*)(&m.tag),sizeof(unsigned short));
	data->append((const char*)(&len),2);
	data->append(value,len);
	return true;
}
bool message2::AddFieldDefine(fielddefine& m,const std::string& name,FieldType /*type_*/)
{
	std::map<std::string,fielddefine>::iterator idx = allfield.find(name);
	if(idx == allfield.end())
	{
		std::map<std::string,FieldInfo*>::iterator it = alldef_field_name.find(name) ;
		if(it == alldef_field_name.end())
		{
			return false ;
		}
		FieldInfo* p = it->second;
		m.type = p->type;
		m.pos = data->length();
		m.tag = p->tag;
		allfield[name] = m;
		return true;
	}
	else	
	{
		m =allfield[name];
		return true;
	}
}
bool message2::get_field_int8(const std::string& name , char& value)
{
	FieldType old = TTINT8;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTINT8)
		return false;
	value = holder->i8;
	return true;
}

bool message2::get_field_uint8(const std::string& name , unsigned char& value)
{
	FieldType old = TTUINT8;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTUINT8)
		return false;
	value = holder->u8;
	return true;
}

bool message2::get_field_int16(const std::string& name , short& value)
{
	FieldType old = TTINT16;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old == TTINT16)
		value = holder->i16;
	else if(old == TTINT8)
		value = holder->i8;
	else 
		return false;
	return true;
}

bool message2::get_field_uint16(const std::string& name , unsigned short& value)
{
	FieldType old = TTUINT16;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old == TTUINT16)
		value = holder->u16;
	else if(old == TTUINT8)
		value = holder->u8;
	else
		return false;
	return true;
}

bool message2::get_field_int32(const std::string& name , int& value)
{
	FieldType old = TTINT32;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	switch(old)
	{
	case TTINT32:
		value = holder->i32;
		break;
	case TTINT16:
		value = holder->i16;
		break;
	case TTINT8:
		value = holder->i8;
		break;
	default:
		return false;
	}
	return true;
}

bool message2::get_field_uint32(const std::string& name , unsigned int& value)
{
	FieldType old = TTUINT32;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	switch(old)
	{
	case TTUINT32:
		value = holder->u32;
		break;
	case TTUINT16:
		value = holder->u16;
		break;
	case TTUINT8:
		value = holder->u8;
		break;
	default:
		return false;
	}
	return true;
}

bool message2::get_field_int64(const std::string& name , long& value)
{
	FieldType old = TTINT64;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	switch(old)
	{
	case TTINT64:
		value = holder->i64;
		break;
	case TTINT32:
		value = holder->i32;
		break;
	case TTINT16:
		value = holder->i16;
		break;
	case TTINT8:
		value = holder->i8;
		break;
	default:
		return false;
	}
	return true;
}

bool message2::get_field_uint64(const std::string& name , unsigned long& value)
{
	FieldType old = TTUINT64;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	switch(old)
	{
	case TTUINT64:
		value = holder->u64;
		break;
	case TTUINT32:
		value = holder->u32;
		break;
	case TTUINT16:
		value = holder->u16;
		break;
	case TTUINT8:
		value = holder->u8;
		break;
	default:
		return false;
	}
	return true;
}

bool message2::get_field_float(const std::string& name , float& value)
{
	FieldType old = TTFLOAT;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTFLOAT)
		return false;
	value = holder->f32;
	return true;
}

bool message2::get_field_double(const std::string& name , double& value)
{
	FieldType old = TTDOUBLE;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;

	if(old == TTDOUBLE){
		memcpy(&value, &holder->f64, sizeof(holder->f64));
//		value = holder->f64 ;
	}
	else if(old == TTFLOAT){
		memcpy(&value, &holder->f32, sizeof(holder->f32));
	}
	else
		return false;
	return true;
}

bool message2::get_field_bool(const std::string& name , bool& value)
{
	FieldType old = TTBOOL;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTBOOL)
		return false;
	value = holder->bv ;
	return true;
}

bool message2::get_field_string(const std::string& name , const char * & value , int& len)
{
	FieldType old = TTSTRING;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTSTRING)
		return false;
	len = holder->raw.len ;
	value = holder->raw.value ;
	return true;
}
bool message2::get_field_raw(const std::string& name , const char * & value , int& len)
{
	FieldType old = TTRAWDATA;
	data_holder2* holder = Getdata_holder2(name,old);
	if(NULL == holder)
		return false;
	if(old != TTRAWDATA)
		return false;
	len = holder->raw.len ;
	value = holder->raw.value ;
	return true;
}
data_holder2* message2::Getdata_holder2(const std::string& name,FieldType& type)
{
	std::map<std::string,fielddefine>::iterator idx = allfield.find(name);
	if(idx == allfield.end())
		return NULL;
	fielddefine* p = &idx->second;
	type = p->type;
	data_holder2* holder  = (data_holder2*)(data->data()+p->pos+sizeof(unsigned short));
	return holder;
}
int message2::decode(const char * buffer , int buflen,int type)
{
	const char *pbuf = buffer ;
	set_msg_type(pbuf[2]);
	if(type == 1)
		return 4;
	int offset = 4;
	const char *psub = (const char *)(buffer + offset) ;
	int len = ::strlen(psub) + 1;
	set_subject(psub);
	offset += len ;

	int code = *(int*)(buffer+offset);
	set_msg_code(code);
	offset += sizeof(int) ;
	if(type == 2)
		return offset;
	offset += decode_raw(buffer + offset , buflen - offset) ;
	flush();

	return offset ;
}	
int message2::decode_raw(const char * buffer , int buflen) 
{
	int offset = 0;		
	while(offset < buflen)
	{
		data_holder2 holder ;
		holder.data = 0 ;
		int tag = 0 ;
		memcpy(&tag , buffer + offset , 2) ;
		offset += 2 ;
		std::map<int,FieldInfo*>::iterator it = alldef_field_tag.find(tag) ;
		if(it == alldef_field_tag.end())
		{
			return 0;
		}
		FieldInfo& def = *it->second;
		switch(def.type)
		{
		case TTINT8 :
			memcpy(&holder.i8 , buffer + offset , 1) ;
			add_field_int8(def.name , holder.i8) ;
			offset += 1 ;
			break ;
		case TTUINT8 :
			memcpy(&holder.u8 , buffer + offset , 1) ;
			add_field_uint8(def.name , holder.u8) ;
			offset += 1 ;
			break ;
			
		case TTINT16 :
			memcpy(&holder.i16 , buffer + offset , 2) ;
			add_field_int16(def.name , holder.i16) ;
			offset += 2 ;
			break ;
		case TTUINT16 :
			memcpy(&holder.u16 , buffer + offset , 2) ;
			add_field_uint16(def.name , holder.u16) ;
			offset += 2 ;
			break ;
			
		case TTINT32 :
			memcpy(&holder.i32 , buffer + offset , 4) ;
			add_field_int32(def.name , holder.i32) ;
			offset += 4 ;
			break ;
		case TTUINT32 :
			memcpy(&holder.u32 , buffer + offset , 4) ;
			add_field_uint32(def.name , holder.u32) ;
			offset += 4 ;
			break ;
			
		case TTINT64 :
			memcpy(&holder.i64 , buffer + offset , 8) ;
			add_field_int64(def.name , holder.i64) ;
			offset += 8 ;
			break ;
		case TTUINT64 :
			memcpy(&holder.u64 , buffer + offset , 8) ;
			add_field_uint64(def.name , holder.u64) ;
			offset += 8 ;
			break ;	
		case TTFLOAT :
			memcpy(&holder.f32 , buffer + offset , 4) ;
			add_field_float(def.name , holder.f32) ;
			offset += 4 ;
			break ;					
		case TTDOUBLE :{
			memcpy(&holder.f64 , buffer + offset , 8) ;
			add_field_double(def.name , holder.f64) ;
			offset += 8 ;
			break ;
			}

		case TTBOOL :
			memcpy(&holder.bv , buffer + offset , 1) ;
			add_field_bool(def.name , holder.bv) ;
			offset += 1 ;
			break ;																			
		case TTSTRING :
			{
				int slen = 0 ;
				memcpy(&slen , buffer + offset , 2) ;
				offset += 2 ;
				add_field_string(def.name , (const char *)(buffer + offset)) ;
				offset += slen ;
			}
			break ;		
		case TTRAWDATA :
			{
				int slen = 0 ;
				memcpy(&slen , buffer + offset , 2) ;
				offset += 2 ;
				add_field_raw(def.name , (const char *)(buffer + offset),slen) ;
				offset += slen ;
			}
			break ;	
		default:
			break ;
		}//end switch		
	}//end while(left > 0)
		
	return offset ;
}
void message3::AddHead(bool bAddNetHead)
{
	if(bAddNetHead == true)
	{
		unsigned int datalen = 0;
		data->append((const char*)&datalen,sizeof(datalen));
	}
	char msg_phead[4];
	unsigned short msg_data_len = 2 + (subject_.length() + 1) + sizeof(int) + (data->length()-start_pos);
	memcpy(msg_phead , &msg_data_len , 2) ;
	msg_phead[2] = get_msg_type() ;
	msg_phead[3] = MESSAGE_MODE_RAW ;
	data->append(msg_phead,4);
	data->append(subject_.data(),subject_.length());
	*data += '\0';
	int code_ = 0;
	data->append((const char*)&code_,sizeof(code_));
}
void message3::flush()
{
	if(bAddNetHead == true)
	{
		unsigned int datalen = data->length() - start_pos - 4;
		memcpy((char*)(data->data() + start_pos),&datalen,sizeof(datalen));
		unsigned short msg_len = datalen - 2;
		memcpy((char*)(data->data() + start_pos + 4),&msg_len,sizeof(msg_len));
	}
	else
	{
		if(start_pos != 0)
		{
			unsigned short datalen = data->length() - 4;
			memcpy((char*)(data->data() + 2),&datalen,sizeof(datalen));
		}
		unsigned short msg_len = data->length() - start_pos - 2;
		memcpy((char*)(data->data() + start_pos),&msg_len,sizeof(msg_len));
	}
}

