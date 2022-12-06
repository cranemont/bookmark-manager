import { createIcon, Icon } from "@chakra-ui/react";
import { IconDefinition } from "@fortawesome/free-regular-svg-icons";

export const FaIcon = ({ icon }: { icon: IconDefinition }) => {
  const displayName = `${icon.prefix} ${icon.iconName}`;
  const [width, height, _, __, svgPath] = icon.icon;

  const createdIcon = createIcon({
    displayName,
    viewBox: `0 0 ${width} ${height}`,
    path:
      typeof svgPath === "string" ? (
        <path fill="currentColor" d={svgPath} />
      ) : (
        <g>
          {svgPath.map((p, ix) => (
            <path key={ix} d={p} />
          ))}
        </g>
      ),
  });

  return <Icon as={createdIcon} />;
};
