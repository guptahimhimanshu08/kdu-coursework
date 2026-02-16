import { useDispatch, useSelector } from "react-redux"
import { useGetConfigQuery } from "../../services/configApi"
import type { RootState } from "../../app/store"
import { setFrequencyId } from "../../features/bookingSlice"
import React from "react"
import styles from "./CleaningFrequency.module.scss"

export const CleaningFrequency = React.memo(() => {

    const { data } = useGetConfigQuery()
    console.log("Rendering CleaningFrequency component")
    const dispatch = useDispatch()

    const selectedCleaningFrequency = useSelector(
        (state: RootState) => state.bookingDraft.selection.frequencyId
    )

    const handleCleaningFrequencySelect = (frequencyId: string) => {
        dispatch(setFrequencyId(frequencyId))
    }

    return (
        <div className={styles.frequencyContainer}>
            <h3>How often do you like cleaning</h3>
            <div>
                {data?.cleaningFrequency.map((frequency) => (
                    <div
                        key={frequency.id}
                        onClick={() => handleCleaningFrequencySelect(frequency.id)}
                        className={`${styles.frequencyCard} ${selectedCleaningFrequency === frequency.id ? styles.selected : ""}`}
                    >
                        {frequency.name}
                    </div>
                ))}
            </div>
        </div>
    )
})