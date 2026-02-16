import { useDispatch, useSelector } from "react-redux"
import { useGetConfigQuery } from "../../services/configApi"
import { setHours, setDate, setStartTime} from "../../features/bookingSlice"
import type { RootState } from "../../app/store"
import styles from "./HoursAndDates.module.scss"

export const HoursAndDates = () => {
    const {data} = useGetConfigQuery()
    const dispatch = useDispatch()
    console.log("Rendering HoursAndDates component");
    

    const selectionTime = useSelector(
        (state: RootState) => state.bookingDraft.selection.startTime
    )
    const selectionHours = useSelector(
        (state: RootState) => state.bookingDraft.selection.hours
    )

    const handleHoursChange = (hours: number) => {
        const minHours = data?.constraints?.minHours ?? 2
        const maxHours = data?.constraints?.maxHours ?? 12
        if (hours >= minHours && hours <= maxHours) {
            dispatch(setHours(hours))
        }
    }

    const handleDateChange = (date: string) => {
        dispatch(setDate(date))
    }

    const handleStartTimeChange = (startTime: string) => {
        dispatch(setStartTime(startTime))
    }

    return (
        <div className={styles.hoursAndDatesContainer}>
            <h2>Choose hours and dates</h2>

            <div className={styles.hoursAndDatesInputsContainer}> {/* taking inputs*/}
                <div className={styles.hoursAndDatesSelectionContainer}>
                    <div className={styles.hoursContainer}>
                        <p>How many hours?</p>
                        <div className={styles.hoursSetter}>
                            <button onClick={() => handleHoursChange(selectionHours - 1)}>-</button>
                            <span>{selectionHours}</span>
                            <button onClick={() => handleHoursChange(selectionHours + 1)}>+</button>
                        </div>
                    </div>
                    <div className={styles.datePickerContainer}>
                        <p>Choose a date</p>
                        <input type="date" onChange={(e) => handleDateChange(e.target.value)}  />
                    </div>
                </div>
                <div className={styles.startTimeContainer}>
                    <p>When do you like to start?</p>
                    <div className={styles.startTimeOptions}>
                        {data?.timeSlots.map((slot) => (
                            <button
                                key={slot.start}
                                disabled={!slot.available}
                                onClick={() => handleStartTimeChange(slot.start)}
                                className={`${styles.timeSlot} ${slot.available ? styles.availableTimeSlot : ""} ${selectionTime === slot.start ? styles.selectedTimeSlot : ""}`}
                            >
                                {slot.name}
                            </button>
                        ))}
                    </div>
                </div>
            </div>
        </div>

    )
}