package sus.keiger.mlgpvp.player;

import java.util.List;

public interface IAudienceMemberHolder extends IAudienceMember
{
    List<IAudienceMember> GetAudienceMembers();
}