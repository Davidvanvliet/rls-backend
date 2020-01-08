package nl.rls.ci.soap.dto.mapper;

import org.dozer.CustomConverter;

public class MessageTypeConverter implements CustomConverter  {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		String source = (String) sourceFieldValue;
		Integer destination = (Integer) existingDestinationFieldValue;
		switch (source) {
		case "TrainCompositionMessage":
			return new Integer(3003);
		case "TrainRunningInformationMessage":
			return new Integer(4005);
		default:
			return destination;
		}
	}

}

// 1000 ConsignmentOrderMessage
//
// 2001 PathCanceledMessage 2002 PathConfirmedMessage 2003 PathDetailsMessage
// 2004 PathDetailsRefusedMessage 2005 PathNotAvailableMessage 2006
// PathRequestMessage 2007 ReceiptConfirmationMessage
//
// --
//
// sector messages
// (Planning) --
// 2500 PathCoordinationMessage
// 2501
// PathSectionNotificationMessage
// -- sector message end --
//
// 3003
// TrainCompositionMessage
// 3006 TrainReadyMessage
// 4001
// TrainDelayCauseMessage
// 4004 TrainRunningForecastMessage
// 4005 TrainRunningInformationMessage
// 4006 TrainRunningInterruptionMessage
//
// --
//
// sector message (Operations) --
// 4500 PassengerTrainCompositionProcessMessage
// 4501 RollingStockRestrictionMessage
// 4504 ChangeOfTrackMessage
// 4505 TrainJourneyModificationMessage
// -- sector message end --
//
//
// 5001
// AlertMessage
// 5002 WagonArrivalNoticeMessage
// 5003
// WagonDeliveryNoticeMessage
// 5004 WagonDepartureNoticeMessage
// 5006 WagonETI_ETA_Message
// 5007 WagonExceptionMessage
// 5008 WagonExceptionReasonMessage
// 5009 WagonInterchangeNoticeMessage
// 5012 WagonReceivedAtInterchangeMessage
// 5013 WagonRefusedAtInterchangeMessage
// 5014 WagonReleaseNoticeMessage
// 5015
// WagonYardArrivalMessage
// 5016 WagonYardDepartureMessage
//
// 6002
// LocationFileDatasetMessage
// 6003 RollingStockDatasetMessage
//
// --sector
// (RU-RU) ---
// 5500 WagonPerformanceMessage
// -- sector end---
//
//
// 6004
// RollingStockDatasetQueryMessage
//
// -- sector (TrainID) begin --
// 8500
// UpateLinkMessage
// 8501 ObjectInfoMessage
// -- sector end --
//
// 9000
// ErrorMessage
