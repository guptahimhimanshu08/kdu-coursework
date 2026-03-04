import { useGetConfigQuery } from "../../services/configApi"
import { useDispatch, useSelector } from "react-redux"
import { setCleaningTypeId } from "../../features/bookingSlice"
import type { RootState } from "../../app/store"
import React from "react"
import styles from "./CleaningType.module.scss"

const CleaningType = () => {
    console.log("Rendering CleaningType component")
    const {data} = useGetConfigQuery()
    const dispatch = useDispatch()
    
    const selectedCleaningType = useSelector(
        (state: RootState) => state.bookingDraft.selection.cleaningTypeId
    )

    const handleCleaningTypeSelect = (typeId: string) => {
        dispatch(setCleaningTypeId(typeId))
    }

    return (
        <div className={styles.cleaningTypeContainer}>
            <h3>What type of cleaning?</h3>
            <div>
                {data?.cleaningTypes.map((type) => (
                    <div 
                        key={type.id}
                        onClick={() => handleCleaningTypeSelect(type.id)}
                        className={`${styles.cleaningTypeCard} ${selectedCleaningType === type.id ? styles.selected : ""}`}
                    >
                        {type.name}
                    </div>
                ))}
            </div>
        </div>
    )
}

export default React.memo(CleaningType)