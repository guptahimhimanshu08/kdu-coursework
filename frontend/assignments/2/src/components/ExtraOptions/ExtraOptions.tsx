import React from "react";
import type { RootState } from "../../app/store"
import { setExtras } from "../../features/bookingSlice";
import { useGetConfigQuery } from "../../services/configApi";
import { useDispatch, useSelector } from "react-redux";
import styles from "./ExtraOptions.module.scss"

export const ExtraOptions = React.memo(() => {
    const { data } = useGetConfigQuery()
    const dispatch = useDispatch()
    console.log("Rendering ExtraOptions component");

    // extras is an array, it will store all the selected extras, if an extra is deselcted, it will be removed from the array. This way we can have multiple extras selected at the same time
    const selectedExtras = useSelector(
        (state: RootState) => state.bookingDraft.selection.extras
    )

    const handleExtraSelect = (extraId: string) => {
       dispatch(setExtras(extraId))
    }
    const iconMap: Record<string, string> = {
        clean_oven: "https://img.icons8.com/ios-filled/50/000000/oven.png",
        clean_windows: "https://img.icons8.com/ios-filled/50/000000/window.png",
        clean_fridge: "https://img.icons8.com/ios-filled/50/000000/fridge.png",
        ironing: "https://img.icons8.com/ios-filled/50/000000/ironing.png"
    };
    return (
        <div className={styles.extraOptionsContainer}>
            <p>Need any extras?</p>
            <div className={styles.extraOptionsList}>
                {data?.extraOptions.map((extra) => {
                    const isSelected = selectedExtras?.includes(extra.id)
                    
                    return (

                        <button
                            key={extra.id}
                            className={`${styles.extraCard} ${isSelected ? styles.selected : ""}`}
                            onClick={() => handleExtraSelect(extra.id)}

                        >
                            <img src={iconMap[extra.id]} alt={extra.id} />
                            <span>{extra.name}</span>
                        </button>
                    );
                })}
            </div>

        </div>
    )
})
