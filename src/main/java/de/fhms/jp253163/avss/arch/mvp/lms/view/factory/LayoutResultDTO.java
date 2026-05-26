package de.fhms.jp253163.avss.arch.mvp.lms.view.factory;

import java.awt.*;
import java.util.Objects;

public class LayoutResultDTO
{
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    public LayoutResultDTO(GridBagLayout layout, GridBagConstraints constraints) throws NullPointerException
    {
        Objects.requireNonNull(layout);
        Objects.requireNonNull(constraints);

        this.layout = layout;
        this.constraints = constraints;
    }

    public GridBagLayout getLayout() {
        return layout;
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }
}
